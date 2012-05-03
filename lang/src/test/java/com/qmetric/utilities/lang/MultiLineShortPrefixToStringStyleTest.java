package com.qmetric.utilities.lang;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public final class MultiLineShortPrefixToStringStyleTest
{
    @Test
    public void testToString()
    {
        final String toString = new Stub(1, "2").toString();
        assertThat(toString, equalTo("MultiLineShortPrefixToStringStyleTest.Stub[a=1\n  b=2\n]"));
    }

    class Stub
    {
        @SuppressWarnings({"UnusedDeclaration"}) private final int a;

        @SuppressWarnings({"UnusedDeclaration"}) private final String b;

        Stub(final int a, final String b)
        {
            this.a = a;
            this.b = b;
        }

        @Override public String toString()
        {
            return ToStringBuilder.reflectionToString(this, MultiLineShortPrefixToStringStyle.INSTANCE);
        }
    }
}