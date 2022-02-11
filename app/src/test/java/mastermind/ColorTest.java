package mastermind;

import org.junit.Test;
import org.junit.Assert;

public class ColorTest {
    
    @Test
    public void noneColorIsNotNull() {
        Assert.assertNotNull(Color.none);
    }
}
