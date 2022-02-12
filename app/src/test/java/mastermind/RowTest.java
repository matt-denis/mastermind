package mastermind;

import org.junit.Assert;
import org.junit.Test;

public class RowTest {
    private final int nrColors = 6;
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

    private Guess getDifferentGuessThan(Guess guess) {
        Color[] colors = new Color[guess.nrColumns()];
        for (int i = 0; i < guess.nrColumns(); i ++) {
            colors[i] = manager.nextColor(guess.getColor(i));
        }
        return new Guess(colors);
    }
    
    @Test
    public void returnsCorrectNumberOfColumns() {
        Row row = new Row(getGuess(4));
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
        Row row = new Row(getGuess(4));
        row.setMatches(2, 2);
        Assert.assertEquals(2, row.fullMatches());
        Assert.assertEquals(2, row.partialMatches());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionForNonsensicalMatches() {
        var row = new Row(getGuess(4));
        row.setMatches(4, 2);
    }

    @Test
    public void fullMatchesReturnCorrectNumber() {
        Guess guess = getGuess(4);
        Row row = new Row(guess);
        Assert.assertEquals(4, row.nrFullMatches(guess));
    }

    @Test
    public void partialMatchesReturnCorrectNumber() {
        Guess guess1 = getGuess(4);
        Guess guess2 = getDifferentGuessThan(guess1);
        Row row1 = new Row(guess1);
        assert row1.nrFullMatches(guess2) == 0;
        Assert.assertEquals(3, row1.nrPartialMatches(guess2));
    }

    @Test
    public void guessMatchesItself() {
        Guess guess = getGuess(4);
        Row row = new Row(guess);
        row.setMatches(4, 0);
        Assert.assertTrue(row.guessMatches(guess));
    }
}