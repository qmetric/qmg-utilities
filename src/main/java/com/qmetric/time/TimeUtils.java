package com.qmetric.time;

import org.joda.time.DateTime;

/**
 * Time utilities. Can freeze time for tests; Uses JodaTime not java Date or Calendar.
 * <p/>
 * <p/>
 * User: dfarr Date: Aug 17, 2010 Time: 5:08:21 PM
 */
public class TimeUtils
{
    private static boolean frozen;

    private static DateTime currentTime;

    /**
     * Freezes time - all calls to time methods will return the same time every time until unfreezeTime is called.
     *
     * This should only be used for
     *
     * @return the TimeUtils
     */
    public static TimeUtils freezeTime()
    {
        frozen = true;
        currentTime = new DateTime();

        return new TimeUtils();
    }

    public void unfreezeAfter(TimeTest test)
    {
        unfreezeTime();
    }

    public static void unfreezeTime()
    {
        frozen = false;
    }

    public static long getCurrentMilliseconds()
    {
        if (frozen)
        {
            return currentTime.getMillis();
        }
        else
        {
            return new DateTime().getMillis();
        }
    }

    public static interface TimeTest
    {

    }
}
