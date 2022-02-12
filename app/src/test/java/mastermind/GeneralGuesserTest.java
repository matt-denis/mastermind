package mastermind;

import org.junit.Assert;
import org.junit.Test;

public class GeneralGuesserTest {

    private final int nrColors = 8;
    private final int nrColumns = 4;
    private TestUtils utils = new TestUtils(nrColors);
    
    @Test
    public void canAddFirstAndSecondGuess() {
        GeneralGuesser guesser = utils.getGeneralGuesser(nrColumns);
        Guess first = guesser.guess();
        Guess second = guesser.guess();
        Assert.assertNotNull(first);
        Assert.assertNotNull(second);
        Assert.assertNotSame(Guess.none, first);
        Assert.assertNotSame(Guess.none, second);
    }

    @Test
    public void canReachMaxGuesses() {
        Table table = utils.getTable(nrColumns);
        ColorManager manager = utils.getCurrentManager();
        Guesser guesser = new GeneralGuesser(table, manager);
        Guess guess = guesser.guess();
        while (guess != Guess.none) {
            table.addRow(new Row(guess, nrColumns - 2, 2));
            guess = guesser.guess();
        } 
        Assert.assertSame(Guess.none, guess);
    }
    
}
