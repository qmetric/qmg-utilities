package com.qmetric.utilities.spreadsheet.xssf;

import java.lang.IllegalArgumentException;import java.lang.Math;import java.lang.String;import java.util.regex.Pattern;

/**
 * Reads a column index value from a cell location code comprised of A-Z columns and numeric rows.
 *
 * Examples:
 *
 * A5 is column 0
 * B10 is column 1
 * Z2 is column 25
 * AA is column 26
 *
 * ... and so on.
 */
public class ColumnIndexParser
{
    private static final Pattern DIGITS = Pattern.compile("[0-9]");

    private static final String EMPTY_STRING = "";

    private static final int INITIAL_INDEX_VALUE = -1;

    private static final int ASCII_OFFSET = 64;

    private static final int BASE_26 = 26;

    public int columnIndexFrom(final String cellLocation)
    {
        final String columnCode = removeRowNumberFrom(cellLocation);

        final int index = calculateIndexFrom(columnCode.toCharArray());

        if (index == INITIAL_INDEX_VALUE)
        {
            throw new IllegalArgumentException(String.format("Couldn't read a column index for cell location %s", cellLocation));
        }

        return index;
    }

    private int calculateIndexFrom(final char[] columnCharacters)
    {
        int index = INITIAL_INDEX_VALUE;
        int base26power = columnCharacters.length - 1;

        for (final char columnCharacter : columnCharacters)
        {
            index += base10ValueOf(columnCharacter, base26power);
            base26power--;
        }

        return index;
    }

    private double base10ValueOf(final char columnCharacter, final int power)
    {
        return Math.pow(BASE_26, power) * valueOf(columnCharacter);
    }

    private int valueOf(final char letter)
    {
        return letter - ASCII_OFFSET;
    }

    private String removeRowNumberFrom(final String cellLocation)
    {
        return DIGITS.matcher(cellLocation).replaceAll(EMPTY_STRING);
    }
}