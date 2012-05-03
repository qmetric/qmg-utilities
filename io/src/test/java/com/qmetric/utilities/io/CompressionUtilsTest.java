package com.qmetric.utilities.io;

import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public final class CompressionUtilsTest
{
    @Test
    public void compressString() throws IOException
    {
        final String compressed = CompressionUtils.compress("whatever");
        assertNotNull(compressed);

        final String expanded = (String) CompressionUtils.expand(compressed);
        assertThat("whatever", equalTo(expanded));
    }

    @Test
    public void compressNull() throws IOException
    {
        final String compressed = CompressionUtils.compress(null);
        assertNotNull(compressed);

        final String expanded = (String) CompressionUtils.expand(compressed);
        assertNull(expanded);
    }

    @Test
    public void compressSerialized() throws IOException
    {
        final String compressed = CompressionUtils.compress(new SerializableStub("whatever"));
        assertNotNull(compressed);

        final SerializableStub expanded = (SerializableStub) CompressionUtils.expand(compressed);
        assertThat("whatever", equalTo(expanded.getData()));
    }

    public static class SerializableStub implements Serializable
    {
        private final String data;

        public SerializableStub(final String data)
        {
            this.data = data;
        }

        public String getData()
        {
            return data;
        }
    }
}