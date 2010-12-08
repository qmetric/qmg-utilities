// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Compression utility.
 */
public final class CompressionUtils
{
    private static final String CHAR_SET = "ISO8859-1";

    private CompressionUtils()
    {

    }

    /**
     * Serializes and compresses an object.
     *
     * @param data Form data parameters.
     * @return An compressed string representation of the form data.
     * @throws IOException If unable to serialize the data to output stream.
     */
    public static String compress(final Serializable data) throws IOException
    {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final GZIPOutputStream gzos = new GZIPOutputStream(os);
        final ObjectOutputStream oos = new ObjectOutputStream(gzos);

        oos.writeObject(data);

        oos.flush();
        oos.close();

        return new String(os.toByteArray(), CHAR_SET);
    }

    /**
     * .
     *
     * @param data String representation of the object.
     * @return The object.
     */
    public static Serializable expand(final String data)
    {
        try
        {
            final ByteArrayInputStream is = new ByteArrayInputStream(data.getBytes(CHAR_SET));
            final GZIPInputStream gzis = new GZIPInputStream(is);
            final ObjectInputStream ois = new ObjectInputStream(gzis);
            final Serializable object = (Serializable) ois.readObject();

            ois.close();

            return object;
        }
        catch (final Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }
}