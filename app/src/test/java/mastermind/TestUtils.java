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
        var manager = getColorManager(nrColors);
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

    Row getDifferentRowThan(Row row) {
        Guess guess = getDifferentGuessThan(row.guess);
        return new Row(guess);
    }

    Table getTable(int nrColumns) {
        checkConsistency(nrColumns);
        return new Table(nrColumns);
    } 

    private ColorManager getColorManager(int nrColors) {
        return new ColorManager(nrColors, factory);
    }

    private void checkConsistency(int nrColumns) {
        if (nrColumns > nrColors)
            throw new IllegalUseOfUtils("number of columns cannot "
            + "exceed the number of different colors set for the utils");
    }
    
}
