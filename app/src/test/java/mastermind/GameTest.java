package mastermind;

import org.junit.Test;
import org.junit.Assert;

public class GameTest {
    
    private final int nrColors = 8;
    private final int nrColumns = 4;
    private final TestUtils utils = new TestUtils(nrColors);

    private Guess getSecret() {
        assert nrColors >= nrColumns * 2;
        ColorManager manager = utils.getCurrentManager();
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
        new Game(utils.getTable(nrColumns), getSecret());
    }

    @Test
    public void canAddGuess() {
        Game game = new Game(utils.getTable(nrColumns), getSecret());
        game.addNewGuess(utils.getGuess(nrColumns));
        // standard utils guess does not match secret
        Assert.assertFalse(game.isFinished());
    }
}
