package com.qmetric.testing.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Created: Jun 9, 2011, Author: Dom Farr
 */

public class StringEndsWithMatcher extends BaseMatcher<String>
{
    private final String expectedString;

    public StringEndsWithMatcher(final String expectedString)
    {
        this.expectedString = expectedString;
    }

    @Override public boolean matches(final Object actualString)
    {
        return (actualString != null) ? (expectedString != null) ? ((String) actualString).endsWith(expectedString) : false : (expectedString == null);
    }

    @Override public void describeTo(final Description description)
    {
        description.appendText("Did not end with [" + expectedString + "]");
    }

    @Factory
    public static Matcher<String> endsWith(String expectedString)
    {
        return new StringEndsWithMatcher(expectedString);
    }
}
