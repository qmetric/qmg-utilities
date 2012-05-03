package com.qmetric.utilities.poi.xssf;

public interface SheetProcessor<T>
{
    RowProcessor getNewRowProcessor(final String sheetName);

    T getResult();
}
