package com.qmetric.utilities.time;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class DefaultTimeSource implements TimeSource
{

    @Override public long getTime()
    {
        return new DateTime().getMillis();
    }
}
