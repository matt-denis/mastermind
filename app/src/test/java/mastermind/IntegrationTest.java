package mastermind;

import org.junit.Test;
import org.junit.Assert;
import static mastermind.PrettyPrintRow.pprint;

public class IntegrationTest {
    
    private static final int NR_COLORS = 6;
    private final static int NR_COLUMNS = 4;
    final ColorManager manager = new ColorManager(NR_COLORS, new LetteredColorFactory());

    private Guess createSecret() {
        final Color[] colors = new Color[NR_COLUMNS];
        Color walk = manager.firstColor();
        for (int i = 0 ; i < NR_COLORS - NR_COLUMNS; i++) {
            walk = manager.nextColor(walk);
        }
        for (int i = NR_COLUMNS - 1; i >=0; i--) {
            colors[i] = walk;
            walk = manager.nextColor(walk);
        }
        return new Guess(colors);
    }

    @Test
    public void playSimpleGame() {
        Table table = new Table(NR_COLUMNS, manager);
        Guess secret = createSecret();
        System.out.println("Secret: " + pprint(new Row(secret, 4, 0)));
        System.out.println();
        Game game = new Game(table, secret);

        Guesser guesser = new UniqueGuesser(table);
        while (!game.isFinished()) {
            Guess guess = guesser.guess();
            if (guess == Guess.none) {
                Assert.fail();
            }
            Row row = game.addNewGuess(guess);
            System.out.println(PrettyPrintRow.pprint(row));
        }

    }

    public static void main(String[] args) {
        new IntegrationTest().playSimpleGame();
    }
    
}
