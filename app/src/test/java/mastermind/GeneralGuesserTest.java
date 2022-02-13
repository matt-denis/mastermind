package mastermind;

import org.junit.Assert;
import org.junit.Test;
import static mastermind.UnitTestUtils.*;

public class GeneralGuesserTest {

    private final int nrColors = 8;
    private final int nrColumns = 4;

    
    @Test
    public void canAddFirstAndSecondGuess() {
        GeneralGuesser guesser = getNewGeneralGuesser(nrColors, nrColumns);
        Guess first = guesser.guess();
        Guess second = guesser.guess();
        Assert.assertNotNull(first);
        Assert.assertNotNull(second);
        Assert.assertNotSame(Guess.none, first);
        Assert.assertNotSame(Guess.none, second);
    }

    @Test
    public void canReachMaxGuesses() {
        Table table = getNewTable(nrColors, nrColumns);
        Guesser guesser = new GeneralGuesser(table);
        Guess guess = guesser.guess();
        while (guess != Guess.none) {
            table.addRow(new Row(guess, nrColumns - 2, 2));
            guess = guesser.guess();
        } 
        Assert.assertSame(Guess.none, guess);
    }
    
}
