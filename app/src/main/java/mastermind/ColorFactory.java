package mastermind;

@FunctionalInterface
public interface ColorFactory {
    /**
     * creates a single @code Color instance
     * @return @code Color instance
     */
    Color create();

    /**
     * 
     * @param n number of colors to create
     * @return Array of colors
     */
    default Color[] createMany(int n) {
        var colors = new Color[n];
        for (int i = 0; i < n; i++) {
            colors[i] = create();
        }
        return colors;
    }
}
