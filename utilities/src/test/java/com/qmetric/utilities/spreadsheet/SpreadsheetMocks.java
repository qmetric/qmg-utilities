package com.qmetric.utilities.spreadsheet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpreadsheetMocks
{
    public static Cell mockStringCell(final String value)
    {
        Cell cell = mock(Cell.class);
        when(cell.getStringCellValue()).thenReturn(value);
        return cell;
    }

    public static Row mockRowWithFirstCell(final String value)
    {
        Row row = mock(Row.class);
        Cell cell = mockStringCell(value);
        when(row.getCell(0)).thenReturn(cell);
        return row;
    }
}

