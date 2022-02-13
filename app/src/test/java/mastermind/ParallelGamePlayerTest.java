package mastermind;

import org.junit.Assert;
import java.util.concurrent.*;

public class ParallelGamePlayerTest {

    private static final int NR_COLORS = 10;
    final ColorManager manager = new ColorManager(NR_COLORS, new LetteredColorFactory());
    private static final int NR_COLUMNS = 6;
    private final int nrThreads;
    private final BlockingQueue<Guess> guessQueue;

    public ParallelGamePlayerTest(int nrThreads, int queueSize) {
        guessQueue = new ArrayBlockingQueue<Guess>(nrThreads * queueSize);
        this.nrThreads = nrThreads;
    }

    private String out = "";

    private void print(String s) {
        System.out.print(s);
        out += s;
    }

    private void println(String s) {
        print(s);
        System.out.println();
        out += "\n";
    }

    private void println() {
        out += "\n";
    }

    public String playParallel() {
        Table table = new Table(NR_COLUMNS, manager);
        Guess secret = createSecret();
        println(PrettyPrintRow.pprint(new Row(secret, NR_COLUMNS, 0)));
        println();
        Game game = new Game(table, secret);
        final IntervalGuesser[] guessers = createGuessers(table);
        startAsynchronousGuessers(guessers);
        final UniqueGuesser finalCheckGuesser = new UniqueGuesser(table);
        int serial = 1;
        try {
            while (!game.isFinished()) {
                final Guess guess = guessQueue.take();
                if (finalCheckGuesser.guessMatch(guess)) {
                    if (guess == Guess.none) {
                        Assert.fail();
                    }
                    Row row = game.addNewGuess(guess);
                    print("" + serial + ". ");
                    serial++;
                    println(PrettyPrintRow.pprint(row));
                }
            }
        } catch (InterruptedException ie) {

        } finally {
            stopAsynchronousGuessers(guessers);
        }
        return out;
    }

    private ExecutorService executorService;
    private void startAsynchronousGuessers(IntervalGuesser[] guessers) {
        executorService = Executors.newFixedThreadPool(nrThreads);
        for (IntervalGuesser guesser : guessers) {
            executorService.execute(guesser);
        }
    }

    private void stopAsynchronousGuessers(IntervalGuesser[] guessers) {
        executorService.shutdown();
    }

    private Guess createSecret() {
        Color[] colors = new Color[NR_COLUMNS];
        int count = 0;
        Color color = manager.firstColor();
        while (count < NR_COLORS - NR_COLUMNS) {
            color = manager.nextColor(color);
            count++;
        }
        for (int i = NR_COLUMNS - 1; i >= 0; i--) {
            colors[i] = color;
            color = manager.nextColor(color);
        }
        return new Guess(colors);
    }

    private IntervalGuesser[] createGuessers(Table table) {
        final Color[] colors = new Color[NR_COLUMNS];
        Guess start = firstIntervalStart(colors);
        final IntervalGuesser[] guessers = new IntervalGuesser[nrThreads];
        for (int i = 0; i < nrThreads - 1; i++) {
            Guess end = nextIntervalStart(colors);
            guessers[i] = new IntervalGuesser(table, start, end, guessQueue);
            start = end;
        }
        guessers[nrThreads - 1] = new IntervalGuesser(table, start, Guess.none, guessQueue);
        for (Guesser guesser : guessers) {
            println(guesser.toString());
        }
        return guessers;
    }


    private Guess firstIntervalStart(Color[] colors) {
        for (int i = 0; i < colors.length; i++) {
            colors[i] = manager.firstColor();
        }
        return new Guess(colors);
    }

    private Guess nextIntervalStart(Color[] colors) {
        int step = NR_COLORS / nrThreads;
        if (step == 0) {
            step = 1;
        }
        while (step > 0) {
            if (manager.thereIsNextColor(colors[0])) {
                colors[0] = manager.nextColor(colors[0]);
                step--;
            } else {
                return Guess.none;
            }
        }
        Guess guess = new Guess(colors);
        while (!guess.isUnique()) {
            guess = guess.nextGuess(manager);
        }
        return guess;
    }

    public static void main(String[] args) {
        new ParallelGamePlayerTest(4, 100).playParallel();
    }
}