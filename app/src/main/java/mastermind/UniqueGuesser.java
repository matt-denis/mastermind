package mastermind;

public class UniqueGuesser extends GuesserBase {

    public UniqueGuesser(Table table) {
        super(table);
    }

    @Override 
    protected Guess firstGuess() {
        var colors = new Color[table.nrColumns()];
        int i = 0;
        for (Color color = manager.firstColor(); i < table.nrColumns();
            color = manager.nextColor(color), i++) {
                colors[i] = color;
        }
        return new Guess(colors);
    }

    @Override
    protected Guess nextGuess() {
        Guess guess;
        do {
            guess = super.nextGuess();
        } while (!guess.isUnique());
        return guess;
    }


    
}
