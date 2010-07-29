package com.qmetric.hamcrest.matchers;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 29, 2010 Time: 4:59:52 PM To change this template use File | Settings | File Templates.
 */
public class CollectionMatcherTest
{
    private static final Collection<String> EXPECTED_COLLECTION = Arrays.asList(new String("1"), new String("2"));

    private static final Collection<String> EXPECTED_COLLECTION_DIFFERENT_ORDER = Arrays.asList(new String("2"), new String("1"));;

    private static final Collection<String> DIFFERENT_COLLECTION = Arrays.asList(new String("1)"));

    @Test
    public void shouldMatchCollection()
    {
        Assert.assertThat(CollectionMatcher.containsOnly(EXPECTED_COLLECTION).matchesSafely(EXPECTED_COLLECTION), equalTo(true));
    }

    @Test
    public void shouldMatchCollectionContainingSameElementsInDifferentOrder()
    {
        Assert.assertThat(CollectionMatcher.containsOnly(EXPECTED_COLLECTION).matchesSafely(EXPECTED_COLLECTION_DIFFERENT_ORDER), equalTo(true));
    }

    @Test
    public void shouldFailToMatchDifferentCollections()
    {
        Assert.assertThat(CollectionMatcher.containsOnly(EXPECTED_COLLECTION).matchesSafely(DIFFERENT_COLLECTION), equalTo(false));
    }
}
