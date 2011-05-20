package com.qmetric.testing.hamcrest.matchers;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 29, 2010 Time: 4:59:52 PM To change this template use File | Settings | File Templates.
 */
public class CollectionMatcherTest
{
    private static final String STR_1 = new String("1");

    private static final String STR_2 = new String("2");

    private static final String STR_3 = new String("3)");

    private static final Collection<String> EXPECTED_COLLECTION = Arrays.asList(STR_1, STR_2);

    private static final Collection<String> EXPECTED_COLLECTION_DIFFERENT_ORDER = Arrays.asList(STR_2, STR_1);

    ;

    private static final Collection<String> DIFFERENT_COLLECTION = Arrays.asList(STR_3);

    @Test
    public void shouldMatchCollection()
    {
        assertThat(CollectionMatcher.containsOnly(EXPECTED_COLLECTION).matchesSafely(EXPECTED_COLLECTION), equalTo(true));
    }

    @Test
    public void shouldMatchCollectionContainingSameElementsInDifferentOrder()
    {
        assertThat(CollectionMatcher.containsOnly(EXPECTED_COLLECTION).matchesSafely(EXPECTED_COLLECTION_DIFFERENT_ORDER), equalTo(true));
    }

    @Test
    public void shouldFailToMatchDifferentCollections()
    {
        assertThat(CollectionMatcher.containsOnly(EXPECTED_COLLECTION).matchesSafely(DIFFERENT_COLLECTION), equalTo(false));
    }

    @Test
    public void shouldMatchArrayCollection()
    {
        assertThat(CollectionMatcher.containsOnly(STR_1, STR_2).matchesSafely(EXPECTED_COLLECTION), equalTo(true));
    }

    @Test
    public void shouldFailArrayIfDifferent()
    {
        assertThat(CollectionMatcher.containsOnly(STR_1, STR_2).matchesSafely(DIFFERENT_COLLECTION), equalTo(false));
    }

    @Test
    public void shouldBeAbleToUseObjectAsMatcher()
    {
        assertThat(Arrays.asList("a", "b", "c"), CollectionMatcher.containsOnly("a", "b", "c"));
    }
}
