package com.qmetric.utilities.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * User: dfarr Date: Oct 29, 2010 Time: 8:57:31 AM
 */
public class StrippedUUIDTest
{
    private String testUUID;

    @Before
    public void context()
    {
        testUUID = new StrippedUUID().generate();
    }

    @Test
    public void shouldReturnUUIDWithoutDashes()
    {
        assertThat(testUUID.indexOf('-'), equalTo(-1));
    }

    @Test
    public void shouldReturnUUIDOf36Characters()
    {
        assertThat(testUUID.length(), equalTo(32));
    }

    @Test
    public void shouldOnlyContainValidCharacters()
    {
        for (char c : testUUID.toCharArray())
        {
            assertTrue("'" + c + "' is not a digit or letter", withinAcceptableRange(c));
        }
    }

    private boolean withinAcceptableRange(final char c)
    {
        return Character.isLetterOrDigit(c);
    }
}
