package com.qmetric.utilities.profiling;

import com.qmetric.utilities.time.DateTimeSource;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class BucketServiceProfilingInterceptor
{

    private static final Logger LOGGER = Logger.getLogger(BucketServiceProfilingInterceptor.class);

    private final DateTimeSource dateTimeSource;

    private final String profilerName;

    public BucketServiceProfilingInterceptor(DateTimeSource dateTimeSource, String profilerName)
    {
        this.dateTimeSource = dateTimeSource;
        this.profilerName = profilerName;
    }

    public Object profile(final ProceedingJoinPoint call) throws Throwable
    {
        if (LOGGER.isTraceEnabled())
        {
            final DateTime start = dateTimeSource.now();
            final Object model = call.proceed();
            final Duration duration = new Duration(start, dateTimeSource.now());

            final String methodName = call.getSignature().getName();
            final String key = getKey(call.getArgs());
            final long time = duration.getMillis();

            LOGGER.trace(String.format("%s|%s|%s|%d", profilerName, methodName, key, time));

            return model;
        }
        else
        {
            return call.proceed();
        }
    }

    private String getKey(final Object[] args)
    {
        return (args.length > 0) ? (String) args[0] : "No key";
    }
}
