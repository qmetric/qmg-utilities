package com.qmetric.utilities.string;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created: Oct 11, 2011, Author: Dom Farr
 */

public class StringUtilsTest
{
    @Test
    public void shouldInsertSpaceBeforeEveryCapitalLetterButTheFirst()
    {
        final String split = new StringUtils().splitOnCapitalLetter("HelloDomWorld");

        assertThat(split, equalTo("Hello Dom World"));
    }

    @Test
    public void shouldNotInsertSpaceOnFirstCapitalLetter()
    {
        final String split = new StringUtils().splitOnCapitalLetter("Hello");

        assertThat(split, equalTo("Hello"));
    }
}