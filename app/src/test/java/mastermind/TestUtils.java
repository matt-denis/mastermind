package mastermind;

public class TestUtils {

    private static class IllegalUseOfUtils extends IllegalArgumentException {
        public IllegalUseOfUtils(String message) {
            super(message);
        }
    }
    

    private final int nrColors;
    private ColorFactory factory = new SimpleColorFactory();
    private ColorManager manager;

    TestUtils(int nrColors) {
        this.nrColors = nrColors;
        manager = new ColorManager(nrColors, factory); 
    };
    
    Color[] getColors(int nrColumns) {
        checkConsistency(nrColumns);
        var colors = new Color[nrColumns];
        Color walk = manager.firstColor();
        for (int i = 0; i < nrColumns; i++, walk = manager.nextColor(walk)) {
            colors[i] = walk;
        }
        return colors;
    }
    Guess getGuess(int nrColumns) {
        assert nrColumns <= nrColors;
        Color[] colors = getColors(nrColumns);
        return new Guess(colors);
    }

    Guess getDifferentGuessThan(Guess guess) {
        Color[] colors = new Color[guess.nrColumns()];
        for (int i = 0; i < guess.nrColumns(); i ++) {
            colors[i] = manager.nextColor(guess.getColor(i));
        }
        return new Guess(colors);
    }

    Row getRow(int nrColumns) {
        Guess guess = getGuess(nrColumns);
        return new Row(guess);
    }

    Row getRow(int nrColumns, int fullMatches, int partialMatches) {
        Row row = getRow(nrColumns);
        row.setMatches(fullMatches, partialMatches);
        return row;
    }

    Row getDifferentRowThan(Row row) {
        Guess guess = getDifferentGuessThan(row.guess);
        return new Row(guess);
    }

    Row getDifferentRowThan(Row row, int fullMatches, int partialMatches) {
        Row other = getDifferentRowThan(row);
        other.setMatches(fullMatches, partialMatches);
        return other;
    }

    Table getTable(int nrColumns) {
        checkConsistency(nrColumns);
        return new Table(nrColumns);
    } 

    GeneralGuesser getGeneralGuesser(int nrColumns) {
        return new GeneralGuesser(getTable(nrColumns), manager);
    }

    UniqueGuesser getUniqueGuesser(int nrColumns) {
        return new UniqueGuesser(getTable(nrColumns), manager);
    }

    ColorManager getNewColorManager(int nrColors) {
        return new ColorManager(nrColors, factory);
    }

    ColorManager getCurrentManager() {
        return manager;
    }

    private void checkConsistency(int nrColumns) {
        if (nrColumns > nrColors)
            throw new IllegalUseOfUtils("number of columns cannot "
            + "exceed the number of different colors set for the utils");
    }
    
}
