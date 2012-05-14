// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.lang;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class MultiLineShortPrefixToStringStyle extends ToStringStyle
{
    public static final ToStringStyle INSTANCE = new MultiLineShortPrefixToStringStyle();

    private static final long serialVersionUID = 1L;

    MultiLineShortPrefixToStringStyle()
    {
        super();
        this.setContentStart("[");
        this.setFieldSeparatorAtStart(false);
        this.setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
        this.setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
        this.setUseShortClassName(true);
        this.setUseIdentityHashCode(false);
    }

    /**
     * <p>Ensure <code>Singleton</code> after serialization.</p>
     *
     * @return the singleton
     */
    private Object readResolve()
    {
        return INSTANCE;
    }
}