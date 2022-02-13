package mastermind;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

/**
 * Represents the state of the game and holds guesses made
 */
public class Table implements Iterable<Row> {

    final List<Row> rows;
    private final int nrColumns;
    final ColorManager manager;

    public Table(int nrColumns, ColorManager manager) {
        this.nrColumns = nrColumns;
        this.manager = manager;
        rows = new CopyOnWriteArrayList<>();
    }

    public int size() { return rows.size(); }

    public boolean isEmpty() { return size() == 0; } 

    public int nrColumns() { return nrColumns; }
    
    public void addRow(Row row) {
        assertCompatibility(row);
        rows.add(row);
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }

    private void assertCompatibility(Row row) {
        if (nrColumns != row.nrColumns())
            throw new IllegalArgumentException("Number of columns of rows on" +
                " the table and of the inserted row must match");
    }
    
}
