package mastermind;

import java.util.Set;
import java.util.HashSet;

public class UniqueGuesser extends GuesserBase {

    public UniqueGuesser(Table table, ColorManager manager) {
        super(table, manager);
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
        } while (guess != Guess.none && !isUnique(guess));
        return guess;
    }

    private boolean isUnique(Guess guess) {
        Set<Color> seen = new HashSet<>();
        for (var color : guess) {
            if (seen.contains(color)) return false;
            seen.add(color);
        }
        return true;
    }
    
}
