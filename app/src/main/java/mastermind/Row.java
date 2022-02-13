package mastermind;

public class Row {
    
    public static Guess none = Guess.none;
    protected final Guess guess;
    protected final int nrColumns;
    final private int partialMatches;
    final private int fullMatches;

    public Row(Guess guess, int fullMatches, int partialMatches) {
        this.guess = guess;
        this.fullMatches = fullMatches;
        this.partialMatches = partialMatches;
        nrColumns = guess.nrColumns();
    }

    protected Row(Row row) {
        this(row.guess, row.fullMatches, row.partialMatches);
    }

    public int nrColumns() { return nrColumns; }

    public int partialMatches() { return partialMatches; }

    public int fullMatches() { return fullMatches; }

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