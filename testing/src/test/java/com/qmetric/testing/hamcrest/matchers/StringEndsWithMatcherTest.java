package com.qmetric.testing.hamcrest.matchers;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created: Jun 9, 2011, Author: Dom Farr
 */

public class StringEndsWithMatcherTest
{
    @Test
    public void shouldMatchTrueForStringsWithMatchingEnds()
    {
        final boolean matchResult = StringEndsWithMatcher.endsWith("abc").matches("zyzabc");

        assertThat(matchResult, equalTo(true));
    }

    @Test
    public void shouldMatchFalseForStringsWithoutMatchingEnds()
    {
        final boolean matchResult = StringEndsWithMatcher.endsWith("abc").matches("abczyz");

        assertThat(matchResult, equalTo(false));
    }

    @Test
    public void shouldMatchTrueForNullActualAndNullExpected()
    {
        final boolean matchResult = StringEndsWithMatcher.endsWith(null).matches(null);

        assertThat(matchResult, equalTo(true));
    }

    @Test
    public void shouldMatchFalseForNullActualAndNotNullExpected()
    {
        final boolean matchResult = StringEndsWithMatcher.endsWith(null).matches("abc");

        assertThat(matchResult, equalTo(false));
    }

    @Test
    public void shouldMatchFalseForNotNullActualAndNullExpected()
    {
        final boolean matchResult = StringEndsWithMatcher.endsWith("xyz").matches(null);

        assertThat(matchResult, equalTo(false));
    }
}
