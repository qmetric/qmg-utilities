package com.qmetric.utilities.time;

import org.joda.time.DateTime;

public class DefaultDateTimeSource implements DateTimeSource
{
    @Override public DateTime now()
    {
        return new DateTime();
    }
}
