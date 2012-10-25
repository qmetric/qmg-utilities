package com.qmetric.utilities.file;

public class RuntimeIOException extends RuntimeException
{
    public RuntimeIOException(final Exception e)
    {
        super(e);
    }

    public RuntimeIOException(final String message)
    {
        super(message);
    }
}
