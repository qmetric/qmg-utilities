package com.qmetric.utilities.poi.xssf;

import org.apache.commons.lang3.StringUtils;

public class Cell
{
    public final static Cell EMPTY_CELL = new Cell(null);

    private final String value;

    private Cell(final String value)
    {
        this.value = value;
    }

    public static Cell newCell(final String value)
    {
        if (StringUtils.isBlank(value))
        {
            return EMPTY_CELL;
        }
        else
        {
            return new Cell(value);
        }
    }

    public String getValue()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        return this == EMPTY_CELL ? o == EMPTY_CELL : (o instanceof Cell ? value.equals(((Cell) o).value) : false);
    }

    public boolean isEmpty()
    {
        return this.equals(EMPTY_CELL);
    }

    public boolean isNotEmpty()
    {
        return !isEmpty();
    }
}
