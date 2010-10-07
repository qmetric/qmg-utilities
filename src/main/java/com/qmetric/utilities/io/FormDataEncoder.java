// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.io;

import com.qmetric.utilities.uuid.UUIDCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Form data encoding utility.
 *
 * @author Initial: aeells, "12-Jun-2007".
 */
public final class FormDataEncoder
{
    private static final String CHAR_SET = "ISO8859-1";

    /**
     * Default constructor. Prevents instantiation.
     */
    private FormDataEncoder()
    {
    }

    /**
     * Serializes and <code>Base64</code> encodes a java object, suitable for inclusion within a form.
     *
     * @param parameterData Form data parameters.
     * @return An encoded string representation of the form data.
     * @throws java.io.IOException If unable to serialize the data to output stream.
     */
    public static String encode(Object parameterData) throws IOException
    {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final GZIPOutputStream gzos = new GZIPOutputStream(os);
        final ObjectOutputStream oos = new ObjectOutputStream(gzos);

        if (parameterData instanceof Map)
        {
            //Certain maps come in as ParameterMap (a catalina class) which fails deserialisation, so rebuild it as a hashmap.
            //noinspection unchecked
            parameterData = new HashMap((Map) parameterData);
        }

        oos.writeObject(parameterData);

        oos.flush();
        oos.close();

        return new String(UUIDCodec.encodeAsByteArray(os.toByteArray()), CHAR_SET);
    }

    /**
     * <code>Base64</code> decodes and deserializes a string representation of a java object.
     *
     * @param formData An encoded string representation of the object.
     * @return The object.
     */
    public static Object decode(final String formData)
    {
        final Object obj;

        try
        {
            final byte[] data = UUIDCodec.decode(formData.getBytes(CHAR_SET));

            final ByteArrayInputStream is = new ByteArrayInputStream(data);
            final GZIPInputStream gzis = new GZIPInputStream(is);
            final ObjectInputStream ois = new ObjectInputStream(gzis);
            //noinspection unchecked
            obj = ois.readObject();
            ois.close();
        }
        catch (Exception e)
        {
            // rethrow
            throw new IllegalArgumentException(e);
        }

        return obj;
    }
}