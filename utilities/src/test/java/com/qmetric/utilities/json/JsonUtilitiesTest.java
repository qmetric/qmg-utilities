package com.qmetric.utilities.json;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public final class JsonUtilitiesTest
{
    @Test
    public void simpleObjectSerializeToJson() throws IOException
    {
        final String json = JsonUtilities.serializeToJson(new Object());
        assertEquals("simple object not serialized correctly!", "{\"class\":\"java.lang.Object\"}", json);
    }
}