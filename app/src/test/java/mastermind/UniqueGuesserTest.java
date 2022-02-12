package mastermind;

import java.util.Set;
import java.util.HashSet;
import org.junit.Test;
import org.junit.Assert;

public class UniqueGuesserTest {

    private final int nrColumns = 4;
    private final int nrColors = 6;
    private final TestUtils utils = new TestUtils(nrColors);

    private Color[] getIncrementalColors() {
        return utils.getColors(nrColumns);
    }

    private boolean isUnique(Guess guess) {
        Set<Color> seen = new HashSet<>();
        for (var color : guess) {
            if (seen.contains(color)) return false;
            seen.add(color);
        }
        return true;
    }

    @Test
    public void firstGuessIsIncremental() {
        UniqueGuesser guesser = utils.getUniqueGuesser(nrColumns);
        Color[] firstColors = getIncrementalColors();
        Guess firstGuess = guesser.guess();
        assert nrColumns == guesser.table.nrColumns();
        for (int i = 0; i < nrColumns; i++) {
            Assert.assertSame(firstColors[i], firstGuess.getColor(i));
        }
    }

    @Test
    public void secondGuessIsUnique() {
        UniqueGuesser guesser = utils.getUniqueGuesser(nrColumns);
        guesser.guess();
        Guess secondGuess =  guesser.guess();
        Assert.assertTrue(isUnique(secondGuess));
    }

    @Test
    public void canReachLastGuessAndAllGuessesUnique() {
        UniqueGuesser guesser = utils.getUniqueGuesser(nrColumns);
        Guess lastGuess;
        do {
            lastGuess = guesser.guess();
            Assert.assertTrue(isUnique(lastGuess));
        } while (lastGuess != Guess.none);
        Assert.assertSame(Guess.none, lastGuess);
    }
    
}
