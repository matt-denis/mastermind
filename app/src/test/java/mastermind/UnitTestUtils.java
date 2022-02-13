package mastermind;

public class UnitTestUtils {
    
    private UnitTestUtils() {}

    static Color[] createColors(int nrColumns, ColorManager manager) {
        final Color[] colors = new Color[nrColumns];
        Color walk = manager.firstColor();
        for (int i = 0; i < nrColumns; i++) {
            colors[i] = walk;
            walk = manager.nextColor(walk);
        }
        return colors;
    }


    static ColorManager getNewManager(int nrColors) { 
        return new ColorManager(nrColors, Color::new);
    }

    static Guess getNewGuess(int nrColors, int nrColumns) {
        assert nrColumns <= nrColors;
        Color[] colors = createColors(nrColumns, getNewManager(nrColors));
        return new Guess(colors);
    }

    static Guess getDifferentGuessThan(Guess guess, int nrColors) {
        final Color[] colors = new Color[guess.nrColumns()];
        final ColorManager manager = getNewManager(nrColors);
        for (int i = 0; i < guess.nrColumns(); i ++) {
            colors[i] = manager.nextColor(guess.getColor(i)); // shift by 1
        }
        return new Guess(colors);
    }

    static Row getNewRow(int nrColors, int nrColumns, int full, int partial) {
        Guess guess = getNewGuess(nrColors, nrColumns);
        return new Row(guess, full, partial);
    }

    static Row getDifferentRowThan(Row row, int full, int partial, int nrColors) {
        Guess guess = getDifferentGuessThan(row.guess, nrColors);
        return new Row(guess, full, partial);
    }

    static Table getNewTable(int nrColors, int nrColumns) {
        checkConsistency(nrColors, nrColumns);
        return new Table(nrColumns, getNewManager(nrColors));
    } 

    static GeneralGuesser getNewGeneralGuesser(int nrColors, int nrColumns) {
        return new GeneralGuesser(getNewTable(nrColors, nrColumns));
    }

    static UniqueGuesser getNewUniqueGuesser(int nrColors, int nrColumns) {
        return new UniqueGuesser(getNewTable(nrColors, nrColumns));
    }

    private static void checkConsistency(int nrColors, int nrColumns) {
        if (nrColumns > nrColors)
            throw new IllegalUseOfUtilsException("number of columns cannot "
            + "exceed the number of different colors set for the utils");
    }
}
