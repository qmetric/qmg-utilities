package com.qmetric.utilities.time;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Specification for TimeUtils
 * <p/>
 * User: dfarr Date: Aug 17, 2010 Time: 5:13:11 PM
 */
public class TimeUtilsTest
{
    @Test
    public void shouldFreezeTime() throws InterruptedException
    {
        TimeUtils.freezeTime().unfreezeAfter(new TimeUtils.TimeTest()
        {
            {
                final long timestampMs = TimeUtils.getCurrentMilliseconds();

                Thread.sleep(50);

                final long timestampMsLater = TimeUtils.getCurrentMilliseconds();

                try
                {
                    assertThat(timestampMs, equalTo(timestampMsLater));
                }
                finally
                {
                    TimeUtils.unfreezeTime();
                }
            }});
    }

    @Test
    public void shouldNotFreezeTime() throws InterruptedException
    {
        final long timestampMs = TimeUtils.getCurrentMilliseconds();

        Thread.sleep(50);

        final long timestampMsLater = TimeUtils.getCurrentMilliseconds();

        assertThat(timestampMs, not(timestampMsLater));
    }
}
