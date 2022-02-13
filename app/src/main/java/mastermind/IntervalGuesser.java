package mastermind;

import java.util.concurrent.BlockingQueue;

public class IntervalGuesser extends UniqueGuesser implements Runnable{

    private Guess start;
    private Guess end;
    private BlockingQueue<Guess> guessQueue;

    public IntervalGuesser(Table table,
        Guess start,
        Guess end,
        BlockingQueue<Guess> guessQueue) {
            super(table);
            this.start = start;
            this.end = end;
            this.guessQueue = guessQueue;
    }

    @Override 
    public void run() {
        Thread.currentThread().setName("guesser " + this);
        var guess = guess();
        try {
            while (guess != Guess.none) {
                guessQueue.put(guess);
                guess = guess();
            }
        } catch (InterruptedException ignore) {}
        
        
    }
    
    @Override
    protected Guess nextGuess() {
        Guess guess = super.nextGuess();
        return guess.equals(end) ? Guess.none : guess;
    }

    @Override 
    public String toString() {
        return String.format("[%s, %s]", start, end);
    }
    
}
