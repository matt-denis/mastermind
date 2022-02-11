package mastermind;

import org.junit.Test;

import org.junit.Assert;

public class SimpleColorFactoryTest {

    private static final int NR_COLORS = 6;
    private ColorFactory factory = new SimpleColorFactory();
    
    @Test
    public void canCreateOneColor() {
        Color color = factory.create();
        Assert.assertNotNull(color);
    }

    @Test
    public void canCreateManyColors() {
        Color[] colors = factory.createMany(NR_COLORS);
        for (var color : colors) Assert.assertNotNull(color);
    }

    @Test
    public void createRightNumberOfColors() {
        Color[] colors = factory.createMany(NR_COLORS);
        Assert.assertEquals(colors.length, NR_COLORS);
    }

    @Test
    public void colorsAreAllDifferent() {
        Color[] colors = factory.createMany(NR_COLORS);
        for (int i = 0; i < NR_COLORS; i++) {
            for (int j = 0; j < NR_COLORS; j++) {
                if (i != j) Assert.assertNotSame(colors[i], colors[j]);
            }
        }
    }
}
