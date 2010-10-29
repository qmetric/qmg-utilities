package com.qmetric.utilities.uuid;

/**
 * Simple wrapper around the system UUID generator which removes the dash characters. i.e. f315c45b-7c5e-4afc-ac50-bd3a265d6503 to f315c45b 7c5e 4afc ac50 bd3a265d6503
 * <p/>
 * User: dfarr Date: Oct 29, 2010 Time: 8:51:14 AM
 */
public class StrippedUUID
{
    private static final char EMPTY_CHAR_LITERAL = (char) 0;

    public String generate()
    {
        return java.util.UUID.randomUUID().toString().replace('-', EMPTY_CHAR_LITERAL);
    }
}
