package com.qmetric.utilities.lang;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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