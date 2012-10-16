package com.qmetric.utilities.profiling;

import com.qmetric.utilities.time.DateTimeSource;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class BucketServiceProfilingInterceptorTest
{

    private static final int DURATION = 10;

    private static final String PROFILER_NAME = "bucketService";

    private static final String METHOD_NAME = "store";

    private static final String KEY = "key";

    private Appender appender;

    private BucketServiceProfilingInterceptor interceptor;

    @Before
    public void setupLogger()
    {
        appender = Mockito.mock(Appender.class);
        Logger.getRootLogger().addAppender(appender);

        final DateTimeSource dateTimeSource = Mockito.mock(DateTimeSource.class);
        final DateTime startTime = new DateTime();
        final DateTime endTime = startTime.plusMillis(DURATION);
        Mockito.when(dateTimeSource.now()).thenReturn(startTime).thenReturn(endTime);

        interceptor = new BucketServiceProfilingInterceptor(dateTimeSource, PROFILER_NAME);
    }

    @Test
    public void shouldNotLogWhenTraceIsNotEnabled() throws Throwable
    {
        Logger.getRootLogger().setLevel(Level.INFO);

        final ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);

        interceptor.profile(pjp);

        Mockito.verify(appender, Mockito.never()).doAppend(Mockito.any(LoggingEvent.class));
        Mockito.verify(pjp).proceed();
    }

    @Test
    public void shouldLogInExpectedFormatWhenTraceIsEnabled() throws Throwable
    {
        Logger.getRootLogger().setLevel(Level.TRACE);

        final ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);

        final Signature signature = Mockito.mock(Signature.class);
        Mockito.when(pjp.getSignature()).thenReturn(signature);
        Mockito.when(signature.getName()).thenReturn(METHOD_NAME);

        Mockito.when(pjp.getArgs()).thenReturn(new String[] {KEY});

        interceptor.profile(pjp);

        final ArgumentCaptor<LoggingEvent> captor = ArgumentCaptor.forClass(LoggingEvent.class);
        Mockito.verify(appender).doAppend(captor.capture());
        Mockito.verify(pjp).proceed();

        final LoggingEvent event = captor.getValue();
        assertThat(event.getMessage().toString(), equalTo(String.format("%s|%s|%s|%d", PROFILER_NAME, METHOD_NAME, KEY, DURATION)));
    }

    @After
    public void clearLogger()
    {
        Logger.getRootLogger().removeAppender(appender);
    }
}
