package mastermind;

public abstract class GuesserBase implements Guesser {

    protected final Table table;
    protected final ColorManager manager;
    protected Guess nextGuess;


    public GuesserBase(Table table) {
        this.table = table;
        manager = table.manager;
        nextGuess = firstGuess();
    }

    public Guess guess() {
        Guess guess;
        do {
            guess = nextGuess();
        } while (nextGuess != Guess.none && !guessMatchesTable(guess));
        return guess;
    }

    protected abstract Guess firstGuess();

    protected Guess nextGuess() {
        var currentGuess = nextGuess; 
        nextGuess = nextGuess.nextGuess(manager);
        return currentGuess;
    }


    private boolean guessMatchesTable(Guess guess) {
        for (Row row : table) {
            if (!row.guessMatches(guess)) return false;
        }
        return true;
    }
    
}
