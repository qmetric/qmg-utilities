package com.qmetric.utilities.time;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class DefaultTimeSource implements TimeSource
{
    private static final DateTime TIME = new DateTime();

    @Override public long getTime()
    {
        return TIME.getMillis();
    }
}
