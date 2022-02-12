package mastermind;

public class Row {
    
    public static Guess none = Guess.none;
    protected final Guess guess;
    protected final int nrColumns;
    private int partialMatches;
    private int fullMatches;

    public Row(Guess guess) {
         this.guess = guess;
         nrColumns = guess.nrColumns(); 
    }

    public Row(Guess guess, int fullMatches, int partialMatches) {
        this(guess);
        setMatches(fullMatches, partialMatches);
    }

    public int nrColumns() { return nrColumns; }

    public int partialMatches() { return partialMatches; }

    public int fullMatches() { return fullMatches; }

    public void setMatches(int nrFullMatches, int nrPartialMatches) {
        if (nrFullMatches + nrPartialMatches > nrColumns)
            throw new IllegalArgumentException("The sum of full and partial matches" +
            " cannot be larger than the number of total colors in the row");
        fullMatches = nrFullMatches;
        partialMatches = nrPartialMatches;
    }

    public boolean guessMatches(Row row) {
        return guessMatches(row.guess);
    }

    public boolean guessMatches(Guess other) {
        return nrFullMatches(other) == fullMatches &&
            nrPartialMatches(other) == partialMatches;
    }

    public int nrFullMatches(Guess other) {
        return guess.nrFullMatches(other);
    }

    public int nrPartialMatches(Guess other) {
        return guess.nrPartialMatches(other);
    }

    
}