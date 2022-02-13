package mastermind;

import org.junit.Test;
import org.junit.Assert;

public class PrintableRowTest {
    
    private final int nrColors = 6;
    private ColorFactory factory = new LetteredColorFactory();
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
        assert nrColumns < nrColors;
        Color[] colors = getColors(nrColumns);
        return new Guess(colors);
    }

    @Test
    public void canGetColorsAndIterate() {
        Guess guess = getGuess(4);
        PrintableRow row = new PrintableRow(new Row(guess, 1, 1));
        for (Color color : row) {
            Assert.assertTrue(color instanceof Color);
        }
    }


    @Test
    public void canPrintRow() {
        Guess guess = getGuess(4);
        PrintableRow row = new PrintableRow(new Row(guess, 1, 1));
        String representation = row.toString();
        Assert.assertTrue(representation.length() == 4);
        Assert.assertTrue(representation.charAt(0) == '0');
        Assert.assertTrue(representation.charAt(1) == '1');
        Assert.assertTrue(representation.charAt(2) == '2');
        Assert.assertTrue(representation.charAt(3) == '3');

        // test correct working of cached representation
        representation = row.toString();
        Assert.assertTrue(representation.length() == 4);
        Assert.assertTrue(representation.charAt(0) == '0');
        Assert.assertTrue(representation.charAt(1) == '1');
        Assert.assertTrue(representation.charAt(2) == '2');
        Assert.assertTrue(representation.charAt(3) == '3');
    }


}
