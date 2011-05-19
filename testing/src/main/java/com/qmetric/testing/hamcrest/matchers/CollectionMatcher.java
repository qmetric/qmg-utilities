package com.qmetric.testing.hamcrest.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.Collection;

public class CollectionMatcher<T> extends TypeSafeMatcher<Collection<? extends T>>
{
    private final Collection<? extends T> expected;

    private CollectionMatcher(Collection<? extends T> expectedCollection)
    {
        this.expected = expectedCollection;
    }

    public static <T, Y extends T> CollectionMatcher<Y> containsOnly(Collection<Y> expected)
    {
        return new CollectionMatcher<Y>(expected);
    }

    public static <T, Y extends T> CollectionMatcher<Y> containsOnly(Y... expected)
    {
        return new CollectionMatcher<Y>(Arrays.asList(expected));
    }

    public void describeTo(Description description)
    {
        description.appendValue(expected);
        description.appendText("Collections didn't match");
    }

    @Override
    public boolean matchesSafely(Collection<? extends T> actual)
    {

        return actual.containsAll(expected) && expected.size() == actual.size();
    }
}
