package com.qmetric.utilities.spreadsheet.xssf;

public interface SheetProcessor<T>
{
    RowProcessor getNewRowProcessor(final String sheetName);

    T getResult();
}
