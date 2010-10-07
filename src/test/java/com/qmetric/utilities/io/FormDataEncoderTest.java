package com.qmetric.utilities.io;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public final class FormDataEncoderTest
{
    @Test
    public void simpleEncodeDecode() throws IOException
    {
        Map<String, String[]> map = new HashMap<String, String[]>();

        map.put("a", new String[] {"a"});
        map.put("b", new String[] {"b", "b", "b"});

        String s = FormDataEncoder.encode(map);

        //noinspection unchecked
        map = (Map<String, String[]>) FormDataEncoder.decode(s);

        assertTrue(map != null);
        assertTrue(map.size() == 2);
        assertTrue(map.containsKey("a"));
        assertTrue(map.containsKey("b"));

        String v[];

        v = map.get("a");

        assertTrue(v.length == 1);
        assertTrue(v[0].equals("a"));

        v = map.get("b");

        assertTrue(v.length == 3);
        assertTrue(v[0].equals("b"));
        assertTrue(v[1].equals("b"));
        assertTrue(v[2].equals("b"));
    }
}