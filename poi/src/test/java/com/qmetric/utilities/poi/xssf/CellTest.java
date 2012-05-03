package com.qmetric.utilities.poi.xssf;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class CellTest
{
    private static final String CELL_VALUE = "A value";

    @Test
    public void cellsWithSameContentsAreEqual()
    {
        assertThat(aCellWithValue(), equalTo(Cell.newCell(CELL_VALUE)));
    }

    @Test
    public void emptyCellShouldReferenceTheSameInstance()
    {
        assertTrue(aCellWithNullValue() == Cell.EMPTY_CELL);
    }

    @Test
    public void zeroLengthStringShouldCreateEmptyCell()
    {
        assertThat(aCellWithEmptyStringValue(), equalTo(Cell.EMPTY_CELL));
    }

    @Test
    public void nullStringShouldCreateEmptyCell()
    {
        assertThat(aCellWithNullValue(), equalTo(Cell.EMPTY_CELL));
    }

    @Test
    public void emptyCellShouldReturnIsEmpty()
    {
        assertTrue(Cell.EMPTY_CELL.isEmpty());
    }

    @Test
    public void cellWithValueShouldNotReturnIsEmpty()
    {
        assertFalse(aCellWithValue().isEmpty());
    }

    @Test
    public void emptyCellShouldNotReturnIsNotEmpty()
    {
        assertFalse(Cell.EMPTY_CELL.isNotEmpty());
    }

    @Test
    public void cellWithValueShouldReturnIsNotEmpty()
    {
        assertTrue(aCellWithValue().isNotEmpty());
    }

    @Test
    public void cellShouldReturnValue()
    {
        assertThat(aCellWithValue().getValue(), equalTo(CELL_VALUE));
    }

    private Cell aCellWithValue()
    {
        return Cell.newCell(CELL_VALUE);
    }

    private Cell aCellWithNullValue()
    {
        return Cell.newCell(null);
    }

    private Cell aCellWithEmptyStringValue()
    {
        return Cell.newCell("");
    }

}
