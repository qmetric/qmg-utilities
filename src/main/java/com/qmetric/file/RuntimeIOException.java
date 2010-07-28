package com.qmetric.file;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 28, 2010 Time: 1:43:41 PM To change this template use File | Settings | File Templates.
 */
public class RuntimeIOException extends RuntimeException
{
    public RuntimeIOException(final Exception e)
    {
        super(e);
    }
}
