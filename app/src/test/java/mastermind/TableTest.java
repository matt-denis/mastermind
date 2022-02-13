package mastermind;

import org.junit.Test;
import org.junit.Assert;
import static mastermind.UnitTestUtils.*;


public class TableTest {

    private final int nrColors = 8;
    private final int nrColumns = 4;

    @Test
    public void canGetSize() {
        Row row1 = getNewRow(nrColors, nrColumns, 2, 1);
        Row row2 = getDifferentRowThan(row1, 1, 1, nrColors);
        Table table = getNewTable(nrColors, nrColumns);
        table.addRow(row1);
        table.addRow(row2);
        Assert.assertTrue(table.size() > 0);
    }

    @Test
    public void canAddRows() {
        Table table = getNewTable(nrColors, nrColumns);
        Row row = getNewRow(nrColors, nrColumns, 2, 1);
        table.addRow(row);
        Assert.assertFalse(table.isEmpty());
    }

    @Test
    public void nrColumnsSetCorrectly() {
        Table table = getNewTable(nrColors, nrColumns);
        Assert.assertEquals(nrColumns, table.nrColumns());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIAEIfAddDifferentLengthRow() {
        Table table = getNewTable(nrColors, nrColumns);
        Row row = new Row(getNewGuess(nrColors, nrColumns + 2), 2, 2);
        table.addRow(row);
    }

    @Test
    public void canIterateThroughTable() {
        Table table = getNewTable(nrColors, nrColumns);
        int i = -1, j = -1;
        for (Row outer : table) {
            i++;
            for (Row inner : table) {
                j++;
                if (i != j) Assert.assertNotSame(outer, inner);
            }
            j = -1;
        }
    }
}
