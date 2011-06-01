// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.profiling;

import com.qmetric.utilities.time.DateTimeSource;
import org.apache.log4j.Appender;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class) @PrepareForTest({Appender.class})
@SuppressStaticInitializationFor("com.qmetric.utilities.profiling.ControllerProfilingInterceptor")
public final class ControllerProfilingInterceptorTest
{
    private final Logger mockLogger = mock(Logger.class);

    private final ProceedingJoinPoint mockCall = mock(ProceedingJoinPoint.class);

    private final Signature mockSignature = mock(Signature.class);

    private final DateTimeSource mockDateTimeSource = mock(DateTimeSource.class);

    private ControllerProfilingInterceptor profiler = new ControllerProfilingInterceptor(mockDateTimeSource);

    @Before
    public void initialise()
    {
        Whitebox.setInternalState(ControllerProfilingInterceptor.class, mockLogger);

        when(mockCall.getSignature()).thenReturn(mockSignature);
        when(mockCall.getArgs()).thenReturn(new Object[] {"test"});
        when(mockSignature.getDeclaringType()).thenReturn(Class.class);
    }

    @Test
    public void profileDisabledTrigger() throws Throwable
    {
        when(mockLogger.isTraceEnabled()).thenReturn(false);

        profiler.profile(mockCall);

        verify(mockLogger, never()).trace(anyString());
    }

    @Test
    public void profileEndabledTrigger() throws Throwable
    {
        when(mockLogger.isTraceEnabled()).thenReturn(true);

        profiler.profile(mockCall);

        verify(mockLogger).trace(anyString());
    }
}