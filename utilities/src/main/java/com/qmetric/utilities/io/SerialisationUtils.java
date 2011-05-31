// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.io;

import java.io.Serializable;

// simple wrapper to defeat "static cling" and make testing simpler.
public final class SerialisationUtils
{
    public byte[] serialise(final Serializable o)
    {
        return org.apache.commons.lang.SerializationUtils.serialize(o);
    }

    public Object deserialise(final byte[] bytes)
    {
        return org.apache.commons.lang.SerializationUtils.deserialize(bytes);
    }
}