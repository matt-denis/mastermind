package mastermind;

import org.junit.Test;
import org.junit.Assert;


public class TableTest {

    private final int nrColors = 8;
    private TestUtils utils = new TestUtils(nrColors);
    private final int nrColumns = 4;

    @Test
    public void canGetSize() {
        Row row1 = utils.getRow(nrColumns);
        Row row2 = utils.getDifferentRowThan(row1);
        Table table = utils.getTable(nrColumns);
        table.addRow(row1);
        table.addRow(row2);
        Assert.assertTrue(table.size() > 0);
    }

    @Test
    public void canAddRows() {
        Table table = utils.getTable(nrColumns);
        Row row = utils.getRow(nrColumns);
        table.addRow(row);
        Assert.assertFalse(table.isEmpty());
    }

    @Test
    public void nrColumnsSetCorrectly() {
        Table table = utils.getTable(nrColumns);
        Assert.assertEquals(nrColumns, table.nrColumns());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsIAEIfAddDifferentLengthRow() {
        Table table = utils.getTable(nrColumns);
        Row row = new Row(utils.getGuess(nrColumns + 2));
        table.addRow(row);
    }

    @Test
    public void canIterateThroughTable() {
        Table table = utils.getTable(nrColumns);
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
