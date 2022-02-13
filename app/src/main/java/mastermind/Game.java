package mastermind;

public class Game {

    private final Table table;
    private final Guess secret;
    final int nrColumns;
    private boolean finished;
    Status status;

    private enum Status {
        WON, LOST;
    }


    public Game(Table table, Guess secret) {
        assertInitializationCompatibility(secret, table);
        this.table = table;
        this.secret = secret;
        nrColumns = secret.nrColumns();
    }

    public Row addNewGuess(Guess guess) {
        assertGameActive();
        final int fullMatches = secret.nrFullMatches(guess);
        final int partialMatches = secret.nrPartialMatches(guess);
        final var row = new Row(guess, fullMatches, partialMatches);
        table.addRow(row);
        if (isWinningGuess(fullMatches)) finished = true;
        return row;
    }

    private boolean isWinningGuess(int fullMatches) {
        return fullMatches == nrColumns;
    }

    boolean isFinished() { return finished; }

    private void assertGameActive() {
        if (isFinished())
            throw new IllegalStateException("Game is finished:" + 
            " to continue palying, create a new one.");
    }

    private static void assertInitializationCompatibility(Guess guess, Table table) {
        if (guess.nrColumns() != table.nrColumns())
            throw new IllegalArgumentException("The number of columns of the secret " +
            "must match the number of columns of the guesses on the table");
    }
    
}
