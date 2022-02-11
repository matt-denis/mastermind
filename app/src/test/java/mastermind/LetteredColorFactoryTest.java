package mastermind;

import org.junit.Test;
import org.junit.Assert;

public class LetteredColorFactoryTest {
    
    @Test
    public void createdColorLetterNotNullAndPrintable() {
        var factory = new LetteredColorFactory();
        Color color = factory.create();
        Assert.assertTrue(color.toString().length() > 0);
    }

    @Test
    public void subsequentLetteredColorsHaveSubsequentLetters() {
        var factory = new LetteredColorFactory();        
        for (int i = 'A'; i <= 'Z'; i++) {
            Assert.assertEquals(String.valueOf((char) i), factory.create().toString());
        }
        for (int i = 'a'; i <= 'z'; i++) {
            Assert.assertEquals(String.valueOf((char) i), factory.create().toString());
        }
    }
}
