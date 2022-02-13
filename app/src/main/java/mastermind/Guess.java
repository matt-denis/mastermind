package mastermind;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Arrays;

public class Guess implements Iterable<Color> {

    final static Guess none = new Guess(new Color[0]);
    private final Color[] colors;
    private final int nrColumns;
    private boolean uniquenessNotCalculated = true;
    private boolean unique;

    public Guess(Color[] colors) {
        this.colors = Arrays.copyOf(colors, colors.length);
        nrColumns = colors.length;
    }

    public int nrColumns() { return nrColumns; }

    public boolean isUnique() {
        if (uniquenessNotCalculated) {
            unique = true;
            final Set<Color> set = new HashSet<>();
            for (final Color color : colors) {
                if (set.contains(color)) {
                    unique = false;
                    break;
                }
                set.add(color);
            }
            uniquenessNotCalculated = false;
        }
        return unique;
    }

    public int nrPartialMatches(Guess guess) {
        assertCompatibility(guess);
        int count = 0;
        for (int i = 0; i < nrColumns; i++) {
            for (int j = 0; j < nrColumns; j++) {
                if (i != j && getColor(i) == guess.getColor(j)) count++;
            }
        }
        return count;
    }

    public int nrFullMatches(Guess guess) {
        assertCompatibility(guess);
        int count = 0;
        for (int i = 0; i < nrColumns; i++) {
            if (getColor(i) == guess.getColor(i)) count++;
        }
        return count;
    }

    public Guess nextGuess(ColorManager manager) {
        final var nextColors = Arrays.copyOf(colors, nrColumns);
        boolean found = false;
        for (int i = nrColumns - 1; i >= 0; i--) {
            if (manager.thereIsNextColor(nextColors[i])) {
                nextColors[i] = manager.nextColor(nextColors[i]);
                found = true;
                break;
            }
            else nextColors[i] = manager.firstColor();
        }
        return found ? new Guess(nextColors) : none;
    }

    public Color getColor(int i) { return colors[i]; }

    @Override
    public Iterator<Color> iterator() {
        return new Iterator<Color>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < nrColumns;
            }
            @Override
            public Color next() {
                return colors[index++];
            }
        };
    }

    @Override 
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Guess)) return false;
        var other = (Guess) o;
        return Arrays.equals(colors, other.colors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(colors);
    }

    @Override
    public String toString() {
        String out = "";
        if (this == Guess.none) out = "none";
        else {
            for (final var color : this.colors) {
                out += color;
            }
        }
        return out;
    }

    private void assertCompatibility(Guess guess) {
        if (nrColumns() != guess.nrColumns())
            throw new IllegalArgumentException("Cannot create subsequent guess from guess of different length");
    }
}