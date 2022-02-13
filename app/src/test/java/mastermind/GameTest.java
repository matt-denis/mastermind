package mastermind;

import org.junit.Test;
import org.junit.Assert;
import static mastermind.UnitTestUtils.*;

public class GameTest {
    
    private final int nrColors = 8;
    private final int nrColumns = 4;

    private Guess getSecret() {
        assert nrColors >= nrColumns * 2;
        ColorManager manager = getNewManager(nrColors);
        Color[] colors = new Color[nrColumns];
        Color color = manager.firstColor();
        for (int i = 0; i < nrColumns; i++) {
            colors[i] = color;
            if (i < nrColors - 2) color = manager.nextColor(manager.nextColor(color));
        }
        return new Guess(colors);
    }

    @Test
    public void canInitializeGame() {
        new Game(getNewTable(nrColors, nrColumns), getSecret());
    }

    @Test
    public void canAddGuess() {
        Game game = new Game(getNewTable(nrColors, nrColumns), getSecret());
        game.addNewGuess(getNewGuess(nrColors, nrColumns));
        // standard utils guess does not match secret
        Assert.assertFalse(game.isFinished());
    }
}
