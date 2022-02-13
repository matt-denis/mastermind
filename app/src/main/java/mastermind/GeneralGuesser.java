package mastermind;

public class GeneralGuesser extends GuesserBase {

    public GeneralGuesser(Table table) {
        super(table);
    }

    @Override 
    protected Guess firstGuess() {
        var colors = new Color[table.nrColumns()];
        for (int i = 0; i < table.nrColumns(); i++) {
            colors[i] = manager.firstColor();
        }
        return new Guess(colors);
    }
    
}
