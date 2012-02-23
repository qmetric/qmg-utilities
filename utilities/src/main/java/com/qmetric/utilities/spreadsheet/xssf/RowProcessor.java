package com.qmetric.utilities.spreadsheet.xssf;

import java.util.List;

public interface RowProcessor
{
    void process(List<Cell> row);
}
