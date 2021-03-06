package com.qmetric.utilities.util;

/**
 * Simple wrapper around the system UUID generator which removes the dash characters. i.e. f315c45b-7c5e-4afc-ac50-bd3a265d6503 to
 * f315c45b 7c5e 4afc ac50 bd3a265d6503
 * <p/>
 * User: dfarr Date: Oct 29, 2010 Time: 8:51:14 AM
 */
public class StrippedUUID
{
    public String generate()
    {
        return java.util.UUID.randomUUID().toString().replaceAll("[-]", "");
    }
}
