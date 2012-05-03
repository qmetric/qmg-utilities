package com.qmetric.utilities.poi;

import com.qmetric.utilities.file.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

public class WorkBookReaderTest
{
    public static final String RATES_SPREADSHEET = "res:spreadsheet/workbook_reader_test.xlsx";

    private WorkBookReader workBookReader;

    @Before
    public void init()
    {
        workBookReader = new WorkBookReader(new FileUtils());
    }

    @Test
    public void canReadWorkBook()
    {
        final Workbook workbook = workBookReader.readWorksheet(RATES_SPREADSHEET);

        assertThat(workbook, not(equalTo(null)));
    }

    @Test
    public void shouldDetermineWhenCellHasContent()
    {
        Cell cell = mockCellWithValue();

        final boolean hasContent = workBookReader.cellHasContent(cell);

        assertThat(hasContent, is(true));
    }

    private Cell mockCellWithValue()
    {
        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CELL_TYPE_STRING);
        when(cell.getStringCellValue()).thenReturn("value");
        return cell;
    }

    @Test
    public void shouldDetermineWhenCellHasNoContent()
    {
        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CELL_TYPE_STRING);
        when(cell.getStringCellValue()).thenReturn(" ");

        final boolean hasContent = workBookReader.cellHasContent(cell);

        assertThat(hasContent, is(false));
    }

    @Test
    public void shouldHandleNullCell()
    {
        final boolean hasContent = workBookReader.cellHasContent(null);

        assertThat(hasContent, is(false));
    }

    @Test
    public void shouldGetValueFromCell()
    {
        Cell cell = mockCellWithValue();

        final String value = workBookReader.valueOf(cell);

        assertThat(value, equalTo("value"));
    }

    @Test
    public void numericCellShouldHaveContent()
    {
        final Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CELL_TYPE_NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(1.0);

        assertThat(workBookReader.cellHasContent(cell), is(true));
    }

    @Test
    public void shouldReturnEmptyStringIfNoValueForCell()
    {
        final String value = workBookReader.valueOf(null);

        assertThat(value, equalTo(""));
    }
}
