package mastermind;

import org.junit.Assert;
import org.junit.Test;
import static mastermind.UnitTestUtils.*;

public class RowTest {
    private final int nrColors = 6;
    private final int nrColumns = 4;
    private ColorFactory factory = new SimpleColorFactory();
    private ColorManager manager = new ColorManager(nrColors, factory);
    
    private Color[] getColors(int nrColumns) {
        var colors = new Color[nrColumns];
        Color walk = manager.firstColor();
        for (int i = 0; i < nrColumns; i++, walk = manager.nextColor(walk)) {
            colors[i] = walk;
        }
        return colors;
    }
    private Guess getGuess(int nrColumns) {
        assert nrColumns <= nrColors;
        Color[] colors = getColors(nrColumns);
        return new Guess(colors);
    }

    private Guess differentGuessThan(Guess guess) {
        Color[] colors = new Color[guess.nrColumns()];
        for (int i = 0; i < guess.nrColumns(); i ++) {
            colors[i] = manager.nextColor(guess.getColor(i));
        }
        return new Guess(colors);
    }
    
    @Test
    public void returnsCorrectNumberOfColumns() {
        Row row = getNewRow(nrColors, nrColumns, 1, 1);
        Assert.assertEquals(4, row.nrColumns());
    }

    @Test
    public void returnsCorrectNumberOfMatchesSetInConstructor() {
        Guess guess = getGuess(4);
        Row row = new Row(guess, 2, 2);
        Assert.assertEquals(2, row.fullMatches());
        Assert.assertEquals(2, row.partialMatches());
    }

    @Test 
    public void canSetMatches() {
        Row row = getNewRow(nrColors, nrColumns, 2, 2);
        Assert.assertEquals(2, row.fullMatches());
        Assert.assertEquals(2, row.partialMatches());
    }

    @Test
    public void fullMatchesReturnCorrectNumber() {
        Guess guess = getGuess(4);
        Row row = new Row(guess, 0, 0);
        Assert.assertEquals(4, row.nrFullMatches(guess));
    }

    @Test
    public void partialMatchesReturnCorrectNumber() {
        Guess guess1 = getGuess(4);
        Guess guess2 = differentGuessThan(guess1);
        Row row1 = new Row(guess1, 1, 1);
        assert row1.nrFullMatches(guess2) == 0;
        Assert.assertEquals(3, row1.nrPartialMatches(guess2));
    }

    @Test
    public void guessMatchesItself() {
        Guess guess = getGuess(4);
        Row row = new Row(guess, 4, 0);
        Assert.assertTrue(row.guessMatches(guess));
    }
}