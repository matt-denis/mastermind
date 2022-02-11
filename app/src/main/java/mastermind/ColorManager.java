package mastermind;

import java.util.Map;
import java.util.HashMap;

public class ColorManager {
    static final Color none = Color.none;
    private final int nrColors;
    private final ColorFactory factory;
    private final Map<Color, Color> successor = new HashMap<>();
    private Color first;

    public ColorManager(int nrColors, ColorFactory factory) {
        this.factory = factory;
        this.nrColors = nrColors;
        createOrdering(nrColors);
    }

    public int nrColors() { return nrColors; }

    Color firstColor() { return first; }

    Color nextColor(Color color) {
        return successor.getOrDefault(color, none);
    }

    boolean thereIsNextColor(Color color) {
        return successor.containsKey(color);
    }

    private Color newColor() { return factory.create(); }

    private Color[] createColors(int nrColors) {
        var colors =  new Color[nrColors];
        for (int i = 0; i < nrColors; i++) colors[i] = newColor();
        return colors;
    }

    private  void createOrdering(int nrColors) {
        Color[] colors = createColors(nrColors);
        first = colors[0];
        for (int i = 0; i < nrColors - 1; i++) {
            successor.put(colors[i], colors[i + 1]);
        }
    }
}
