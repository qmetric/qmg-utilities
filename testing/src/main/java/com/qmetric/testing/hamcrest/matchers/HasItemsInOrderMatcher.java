package com.qmetric.testing.hamcrest.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created: Jun 29, 2011, Author: Dom Farr
 */

public class HasItemsInOrderMatcher<T> extends TypeSafeMatcher<T[]>
{
    private final T[] expected;

    private HasItemsInOrderMatcher(T[] expected)
    {
        this.expected = expected;
    }

    @Override public boolean matchesSafely(final T[] actual)
    {
        if (actual == null && expected == null)
        {
            return true;
        }
        if (actual != null && expected == null)
        {
            return false;
        }
        if (actual.length != expected.length)
        {
            return false;
        }

        for (int i = 0; i < actual.length; i++)
        {
            if (!actual[i].equals(expected[i]))
            {
                return false;
            }
        }

        return true;
    }

    @Override public void describeTo(Description description)
    {
        description.appendValue(expected);
        description.appendText("Collections didn't match");
    }

    public static <T, Y extends T> HasItemsInOrderMatcher<Y> containsInOrder(Y... expected)
    {
        return new HasItemsInOrderMatcher<Y>(expected);
    }
}

