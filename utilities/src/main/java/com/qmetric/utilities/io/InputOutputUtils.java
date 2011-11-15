// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// simple wrapper to defeat "static cling" and make testing simpler.
public class InputOutputUtils
{
    public byte[] toByteArray(final ByteArrayInputStream byteArrayInputStream) throws IOException
    {
        return org.apache.commons.io.IOUtils.toByteArray(byteArrayInputStream);
    }

    public int copy(final InputStream inputStream, final OutputStream outputStream) throws IOException
    {
        return org.apache.commons.io.IOUtils.copy(inputStream, outputStream);
    }

    public void closeQuietly(final InputStream inputStream) throws IOException
    {
        org.apache.commons.io.IOUtils.closeQuietly(inputStream);
    }

    public void closeQuietly(final OutputStream outputStream) throws IOException
    {
        org.apache.commons.io.IOUtils.closeQuietly(outputStream);
    }
}