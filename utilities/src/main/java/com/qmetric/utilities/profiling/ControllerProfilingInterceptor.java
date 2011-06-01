// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.profiling;

import com.qmetric.utilities.time.DateTimeSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;

// Log4j configuration (TRACE) triggers whether performance statistics are recorded
public final class ControllerProfilingInterceptor
{
    private static final Logger LOGGER = Logger.getLogger(ControllerProfilingInterceptor.class);

    private final DateTimeSource dateTimeSource;

    @Autowired public ControllerProfilingInterceptor(final DateTimeSource dateTimeSource)
    {
        this.dateTimeSource = dateTimeSource;
    }

    public Object profile(final ProceedingJoinPoint call) throws Throwable
    {
        if (LOGGER.isTraceEnabled())
        {
            final DateTime start = dateTimeSource.now();
            final Object model = call.proceed();
            final Duration duration = new Duration(start, dateTimeSource.now());

            final StringBuilder sb = new StringBuilder(StringUtils.substringBefore(call.getSignature().getDeclaringType().getSimpleName(), "$")).
                    append("|").append(call.getSignature().getName()).append("|");

            for (final Object arg : call.getArgs())
            {
                sb.append(arg).append("|");
            }

            LOGGER.trace(sb.append(duration.getMillis()));

            return model;
        }
        else
        {
            return call.proceed();
        }
    }
}