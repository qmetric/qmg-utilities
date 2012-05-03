package com.qmetric.utilities.poi.xssf;

public interface RowCollator
{
    void startRow();

    void newCell(String location);

    void newSharedStringsCell(String location);

    void setCellValue(String text);

    void finishRow();
}
