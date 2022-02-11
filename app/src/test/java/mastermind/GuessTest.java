package mastermind;

import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
import org.junit.Assert;

public class GuessTest {

    private static int nrColors = 6;
    private static int nrColumns = 4;
    private ColorFactory factory = new SimpleColorFactory();
    private ColorManager manager = new ColorManager(nrColors, factory);
    
    private Color[] getColors() {
        Color[] colors = new Color[nrColumns];
        int i = 0;
        for (Color walk = manager.firstColor(); i < nrColumns;
                walk = manager.nextColor(walk), i++) {
            colors[i] = walk;
        }
        return colors;
    }

    private void swap(Color[] colors, int i, int j) {
        var tmp = colors[i];
        colors[i] = colors[j];
        colors[j] = tmp;
    }

    @Test
    public void returnCorrectNumberOfColumns() {
        Color[] colors = getColors();
        Guess guess = new Guess(colors);
        Assert.assertEquals(colors.length, guess.nrColumns());
    }
        

    @Test
    public void canGetAllColors() {
        Color[] colors = getColors();
        Guess guess = new Guess(colors);
        Assert.assertEquals(colors.length, guess.nrColumns());
        Set<Color> seen = new HashSet<Color>();
        for (int i = 0; i < guess.nrColumns(); i++) {
            var currColor = guess.getColor(i);
            if (!seen.contains(currColor)) seen.add(currColor);
        }
        Assert.assertEquals(colors.length, seen.size());
    }

    @Test
    public void numberOfFullMatchesCorrectlyCalculated() {
        Color[] colors = getColors();
        Guess guess = new Guess(colors);
        Assert.assertEquals(colors.length, guess.nrFullMatches(guess));

        swap(colors, 0, 1);
        Guess guess2 = new Guess(colors);
        Assert.assertEquals(colors.length - 2, guess.nrFullMatches(guess2));

        Color lastInArray = colors[colors.length - 2];
        colors[colors.length - 2] = manager.nextColor(lastInArray);
        lastInArray = colors[colors.length - 2];
        colors[colors.length - 1] = manager.nextColor(lastInArray);
        Guess guess3 = new Guess(colors);
        Assert.assertEquals(colors.length - 4, guess.nrFullMatches(guess3));

    }

    @Test
    public void numberOPartialMatchesCorrectlyCalculated() {
        Color[] colors = getColors();
        Guess guess = new Guess(colors);
        Assert.assertEquals(0, guess.nrPartialMatches(guess));
        swap(colors, 0, 2); swap(colors, 1, 3);
        Guess guess2 = new Guess(colors);
        Assert.assertEquals(4, guess.nrPartialMatches(guess2));
        Assert.assertEquals(0, guess.nrFullMatches(guess2));
    }

    @Test
    public void firstAndOnlyNoneGuessElementIsSameAsNoneColor() {
        Assert.assertTrue(Guess.none.nrColumns() == 1);
        Assert.assertSame(Color.none, Guess.none.getColor(0));
    }

    @Test
    public void consequentColorsMakeUniqueGuess() {
        Color[] colors = getColors();
        Guess guess = new Guess(colors);
        Assert.assertTrue(guess.isUnique());
    }

    @Test
    public void canMakeDifferentGuesses() {
        Color[] colors = getColors();
        Guess guess = new Guess(colors);
        Guess nextGuess = guess.nextGuess(manager);
        Assert.assertTrue(guess.nrFullMatches(nextGuess) < guess.nrColumns());
    }

    @Test(expected = IllegalArgumentException.class) 
    public void canThrowIAEForForDifferentLengthGuesses()
    {
        Guess guess = new Guess(getColors());
        Color[] other = new Color[nrColors + 2];
        Color walk = manager.firstColor();
        for (int i = 0; i < nrColors + 2; i++, walk = manager.nextColor(walk)) {
            other[i] = walk;
        }
        var otherGuess = new Guess(other);
        guess.nrFullMatches(otherGuess);
        guess.nrPartialMatches(otherGuess);
    }

}
