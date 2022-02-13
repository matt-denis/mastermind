package mastermind;

public class PrettyPrintRow {
    
    private PrettyPrintRow() {}

    public static String pprint(Row row) {
        var prow = new PrintableRow(row);
        return String.format("%s full: %d -- partial: %d",
        prow, prow.fullMatches(), prow.partialMatches()); 

    }
}
