package mastermind;

import java.util.Iterator;

public class PrintableRow extends Row implements Iterable<Color> {

    private String cachedRepresentation;
    private boolean cachedRepresentationNotSet = true;
    
    public PrintableRow(Row row) {
        super(row);
    }

    public Color getColor(int i) {
        return guess.getColor(i);
    }

    @Override
    public String toString() {
        if (cachedRepresentationNotSet) {
            cachedRepresentationNotSet = false;
            if (getColor(0) instanceof LetteredColor) {
                final StringBuilder buffer = new StringBuilder();
                for (final var color : this) {
                buffer.append(color.toString());
                }
                cachedRepresentation = buffer.toString();
            }
            else cachedRepresentation = "";
        }
        return cachedRepresentation;
    }

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
                return guess.getColor(index++);
            }
        };
    }
}
