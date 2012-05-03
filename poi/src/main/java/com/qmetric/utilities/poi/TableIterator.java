package com.qmetric.utilities.poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;

public class TableIterator implements Iterable<Row>, Iterator<Row>
{
    private Sheet worksheet;

    private int currentRow;

    public TableIterator(final Sheet worksheet, final int startingRow)
    {
        this.worksheet = worksheet;
        this.currentRow = startingRow;
    }

    @Override public Iterator<Row> iterator()
    {
        return this;
    }

    @Override public boolean hasNext()
    {
        return worksheet.getRow(currentRow) != null && worksheet.getRow(currentRow).getCell(0) != null;
    }

    @Override public Row next()
    {
        return worksheet.getRow(currentRow++);
    }

    @Override public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
