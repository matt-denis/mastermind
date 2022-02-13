package mastermind;

public class IntegrationTestUtils {
    

    private final int nrColors;
    private final int nrColumns;
    private final Color[] colors;
    private final Guess guess;
    private final ColorFactory factory;
    private final ColorManager manager;
    private final Table table;
    private final GeneralGuesser generalGuesser;
    private final UniqueGuesser uniqueGuesser;

    
    public IntegrationTestUtils(int nrColors, int nrColumns, ColorFactory factory) {
        checkConsistency(nrColors, nrColumns);
        this.nrColors = nrColors;
        this.nrColumns = nrColumns;
        this.factory = factory;
        manager = new ColorManager(nrColors, factory);
        colors = createColors(nrColumns, manager);
        guess = new Guess(colors);
        table = new Table(nrColumns, manager);
        generalGuesser = new GeneralGuesser(table);
        uniqueGuesser = new UniqueGuesser(table);

    }

    int nrColors() { return nrColors; }

    int nrColumns() { return nrColumns; }

    Color[] getColors() { return colors; }

    ColorFactory getFactory() { return factory; }

    ColorManager getManager() { return manager; }

    Guess getGuess() { return guess; }

    Table getTable() { return table; }

    GeneralGuesser getGeneralGuesser() { return generalGuesser; }

    UniqueGuesser getUniqueGuesser() { return uniqueGuesser; }

    private static Color[] createColors(int nrColumns, ColorManager manager) {
        final Color[] colors = new Color[nrColumns];
        Color walk = manager.firstColor();
        for (int i = 0; i < nrColumns; i++) {
            colors[i] = walk;
            walk = manager.nextColor(walk);
        }
        return colors;
    }


    ColorManager getNewManager(int nrColors) { 
        return new ColorManager(nrColors, Color::new);
    }

    Guess getNewGuess(int nrColors, int nrColumns) {
        assert nrColumns <= nrColors;
        Color[] colors = createColors(nrColumns, getNewManager(nrColors));
        return new Guess(colors);
    }

    Guess getDifferentGuessThan(Guess guess) {
        final Color[] colors = new Color[guess.nrColumns()];
        for (int i = 0; i < guess.nrColumns(); i ++) {
            colors[i] = manager.nextColor(guess.getColor(i)); // shift by 1
        }
        return new Guess(colors);
    }

    Row getNewRow(int nrColors, int nrColumns, int full, int partial) {
        Guess guess = getNewGuess(nrColors, nrColumns);
        return new Row(guess, full, partial);
    }

    Row getDifferentRowThan(Row row, int full, int partial) {
        Guess guess = getDifferentGuessThan(row.guess);
        return new Row(guess, full, partial);
    }

    Table getNewTable(int nrColors, int nrColumns) {
        checkConsistency(nrColors, nrColumns);
        return new Table(nrColumns, getNewManager(nrColors));
    } 

    GeneralGuesser getNewGeneralGuesser(int nrColors, int nrColumns) {
        return new GeneralGuesser(getNewTable(nrColors, nrColumns));
    }

    UniqueGuesser getUniqueGuesser(int nrColors, int nrColumns) {
        return new UniqueGuesser(getNewTable(nrColors, nrColumns));
    }

    private static void checkConsistency(int nrColors, int nrColumns) {
        if (nrColumns > nrColors)
            throw new IllegalUseOfUtilsException("number of columns cannot "
            + "exceed the number of different colors set for the utils");
    }
    
}
