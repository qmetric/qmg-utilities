package com.qmetric.utilities.uuid;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User: dfarr Date: Oct 29, 2010 Time: 8:57:31 AM
 */
public class StrippedUUIDTest
{
    @Test
    public void shouldReturnUUIDWithoutDashes() {
        assertThat(new StrippedUUID().generate().indexOf('-'), equalTo(-1));
    }

    @Test
    public void shouldReturnUUIDOf36Characters() {
        assertThat(new StrippedUUID().generate().length(), equalTo(36));
    }
}
