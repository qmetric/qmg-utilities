package com.qmetric.utilities.time;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class DefaultDateTimeSource implements DateTimeSource
{
    @Override public DateTime now()
    {
        return new DateTime();
    }
}
