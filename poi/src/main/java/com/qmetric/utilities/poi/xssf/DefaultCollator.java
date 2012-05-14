package com.qmetric.utilities.poi.xssf;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

import static com.qmetric.utilities.poi.xssf.Cell.EMPTY_CELL;

public class DefaultCollator implements RowCollator
{
    private final SharedStringsTableLookup sharedStringsTableLookup;

    private final RowProcessor processor;

    private ArrayList<Cell> row;

    private int currentCellIndex = 0;

    private boolean isSharedStringValue;

    private final ColumnIndexParser columnIndexParser;

    public DefaultCollator(final SharedStringsTableLookup sharedStringsLookup, final RowProcessor processor, final ColumnIndexParser columnIndexParser)
    {
        this.sharedStringsTableLookup = sharedStringsLookup;
        this.processor = processor;
        this.columnIndexParser = columnIndexParser;
    }

    @Override public void startRow()
    {
        row = new ArrayList<Cell>();
    }

    @Override public void newCell(final String location)
    {
        int indexFromLocation = columnIndexParser.columnIndexFrom(location); // starts at zero

        padRowToCorrectLengthFor(indexFromLocation);

        setCurrentCellIndexTo(indexFromLocation);
    }

    private void setCurrentCellIndexTo(final int idx)
    {
        currentCellIndex = idx;
    }

    private void padRowToCorrectLengthFor(final int idx)
    {
        while (row.size() <= idx)
        {
            row.add(EMPTY_CELL);
        }
    }

    @Override public void newSharedStringsCell(final String location)
    {
        newCell(location);

        isSharedStringValue = true;
    }

    @Override public void setCellValue(String value)
    {
        value = checkSharedStringsFor(value);
        if (StringUtils.isNotBlank(value))
        {
            value = value.trim();
        }

        row.set(currentCellIndex, Cell.newCell(value));
    }

    private String checkSharedStringsFor(String text)
    {
        if (isSharedStringValue)
        {
            text = sharedStringsTableLookup.lookupValueFor(text);
            isSharedStringValue = false;
        }

        return text;
    }

    @Override public void finishRow()
    {
        processor.process(Collections.unmodifiableList(row));
    }
}
