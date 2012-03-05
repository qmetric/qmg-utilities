package com.qmetric.utilities.spreadsheet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import static com.qmetric.utilities.spreadsheet.SpreadsheetMocks.mockRowWithFirstCell;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LookupTableIteratorTest
{
    private Row EMPTY_ROW = null;

    private Sheet sheet = mock(Sheet.class);

    private TableIterator it = new TableIterator(sheet, 1);

    private static final int EXPECTED_NUMBER_OF_ROWS = 3;

    @Test
    public void shouldStartFromSpecifiedRowNumber()
    {
        Row headersRow = mock(Row.class);
        when(sheet.getRow(0)).thenReturn(headersRow);
        Row firstRow = mockRowWithFirstCell("0.0001");
        when(sheet.getRow(1)).thenReturn(firstRow);

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), equalTo(firstRow));
    }

    @Test
    public void noCellsInWorksheet()
    {
        when(sheet.getRow(1)).thenReturn(EMPTY_ROW);
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void iteratesCorrectNumberOfTimes()
    {
        mockRows();

        assertThat(it.hasNext(), is(true));

        assertThat(it.next().getCell(0).getStringCellValue(), equalTo("0.0001"));
        assertThat(it.next().getCell(0).getStringCellValue(), equalTo("0.0002"));
        assertThat(it.next().getCell(0).getStringCellValue(), equalTo("0.0003"));

        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void canIterateOverRows()
    {
        mockRows();

        int count = 0;
        for (Row row : it)
        {
            count++;
        }

        assertThat(count, equalTo(EXPECTED_NUMBER_OF_ROWS));
    }

    private void mockRows()
    {
        final Row firstRow = mockRowWithFirstCell("0.0001");
        when(sheet.getRow(1)).thenReturn(firstRow);

        final Row secondRow = mockRowWithFirstCell("0.0002");
        when(sheet.getRow(2)).thenReturn(secondRow);

        final Row thirdRow = mockRowWithFirstCell("0.0003");
        when(sheet.getRow(EXPECTED_NUMBER_OF_ROWS)).thenReturn(thirdRow);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void doesNotSupportRemove()
    {
        it.remove();
    }
}
