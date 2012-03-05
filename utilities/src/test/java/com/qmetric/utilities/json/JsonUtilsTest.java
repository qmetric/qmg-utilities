package com.qmetric.utilities.json;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public final class JsonUtilsTest
{
    @Test
    public void simpleObjectSerializeToJson() throws IOException
    {
        final String json = new JsonUtils().serializeToJson(new Object());
        assertEquals("simple object not serialized correctly!", "{\"class\":\"java.lang.Object\"}", json);
    }
}