package mastermind;

import org.junit.Test;import org.junit.Assert;

public class ColorManagerTest {
    private static final int NR_COLORS = 6;
    private static final ColorFactory FACTORY= new SimpleColorFactory();

    @Test
    public void thereIsNextColor() {
        var manager = new ColorManager(NR_COLORS, FACTORY);
        var first = manager.firstColor();
        var next = manager.nextColor(first);
        Assert.assertTrue(manager.thereIsNextColor(first));
        Assert.assertNotSame(next, Color.none);
    }

    @Test
    public void subsequentColorsAllDifferent() {
        var manager = new ColorManager(NR_COLORS, FACTORY);
        Color walk = manager.firstColor();
        Assert.assertNotNull(walk);
        while (walk != null) {
            Color next = manager.nextColor(walk);
            Assert.assertNotSame(walk, next);
            walk = next;
        }
    }
}