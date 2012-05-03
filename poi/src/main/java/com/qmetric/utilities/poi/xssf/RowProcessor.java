package com.qmetric.utilities.poi.xssf;

import java.util.List;

public interface RowProcessor
{
    void process(List<Cell> row);
}
