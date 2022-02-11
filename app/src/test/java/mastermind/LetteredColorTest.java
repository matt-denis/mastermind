package mastermind;

import org.junit.Assert;
import org.junit.Test;

public class LetteredColorTest {
    
    @Test
    public void colorCanPrintLetter() {
        var color = new LetteredColor('A');
        Assert.assertTrue(color.toString().length() == 1);
    }
}
