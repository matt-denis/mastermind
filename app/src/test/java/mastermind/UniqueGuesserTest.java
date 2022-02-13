package mastermind;

import org.junit.Test;
import org.junit.Assert;
import static mastermind.UnitTestUtils.*;

public class UniqueGuesserTest {

    private final int nrColumns = 4;
    private final int nrColors = 6;

    @Test
    public void firstGuessIsIncremental() {
        ColorManager manager = new ColorManager(nrColors, Color::new);
        Table table = new Table(nrColumns, manager);
        Guesser guesser = new UniqueGuesser(table);
        Guess firstGuess = guesser.guess();
        assert nrColumns == table.nrColumns();
        Color first = manager.firstColor();
        Assert.assertSame(first, firstGuess.getColor(0));
        Color walk = first;
        for (int i = 1; i < nrColumns; i++) {
            walk = manager.nextColor(walk);
            Assert.assertSame(walk, firstGuess.getColor(i));
        }
    }

    @Test
    public void secondGuessIsUnique() {
        UniqueGuesser guesser = getNewUniqueGuesser(nrColors, nrColumns);
        guesser.guess();
        Guess secondGuess = guesser.guess();
        Assert.assertTrue(secondGuess.isUnique());
    }

    @Test
    public void canReachLastGuessAndAllGuessesUnique() {
        UniqueGuesser guesser = getNewUniqueGuesser(nrColors, nrColumns);
        Guess lastGuess;
        do {
            lastGuess = guesser.guess();
            Assert.assertTrue(lastGuess.isUnique());
        } while (lastGuess != Guess.none);
        Assert.assertSame(Guess.none, lastGuess);
    }

    @Test
    public void generateAllGuesses() {
        var guesser = getNewUniqueGuesser(nrColors, nrColumns);
        int count = 0;
        while (guesser.nextGuess() != Guess.none) count++;
        Assert.assertEquals(6 * 5 * 4 * 3, count);
    }
    
}
