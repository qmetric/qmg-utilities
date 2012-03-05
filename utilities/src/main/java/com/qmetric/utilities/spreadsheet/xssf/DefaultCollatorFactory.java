package com.qmetric.utilities.spreadsheet.xssf;

public class DefaultCollatorFactory
{
    private ColumnIndexParser columnIndexParser;

    public DefaultCollatorFactory(final ColumnIndexParser columnIndexParser)
    {
        this.columnIndexParser = columnIndexParser;
    }

    public RowCollator create(final SharedStringsTableLookup sharedStringsTableLookup, final RowProcessor rowProcessor)
    {
        return new DefaultCollator(sharedStringsTableLookup, rowProcessor, columnIndexParser);
    }
}
