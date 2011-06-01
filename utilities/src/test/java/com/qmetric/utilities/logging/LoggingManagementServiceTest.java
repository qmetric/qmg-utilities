package com.qmetric.utilities.logging;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(PowerMockRunner.class) @PrepareForTest({LogManager.class})
public final class LoggingManagementServiceTest
{
    @Test
    public void toggleRootLoggerWarnOffWhenNoLoggerExists() // defend anyway
    {
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getRootLogger()).thenReturn(null);

        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final String logLevel = loggingManagementService.toggleRootLoggerWarnOff();
        assertThat(logLevel, equalTo(StringUtils.EMPTY));
    }

    @Test
    public void toggleRootLoggerWarnOff()
    {
        final LoggingManagementService loggingManagementService = new LoggingManagementService();

        final String warn = loggingManagementService.toggleRootLoggerWarnOff();
        assertThat(warn, equalTo(Level.WARN.toString()));

        final String off = loggingManagementService.toggleRootLoggerWarnOff();
        assertThat(off, equalTo(Level.OFF.toString()));
    }

    @Test
    public void toggleConfiguredLoggersTraceOffWhenNoLoggersExist() // feasible scenario, unlike the missing root logger
    {
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getRootLogger()).thenReturn(null);

        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final String logLevel = loggingManagementService.toggleConfiguredLoggersTraceOff();
        assertThat(logLevel, equalTo(StringUtils.EMPTY));
    }

    @Test
    public void toggleConfiguredLoggersTraceOff()
    {
        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final List<String> configuredForToggle = new ArrayList<String>();
        configuredForToggle.add(LoggingManagementService.class.getName());
        loggingManagementService.setConfiguredForToggle(configuredForToggle);

        final String off = loggingManagementService.toggleConfiguredLoggersTraceOff();
        assertThat(off, equalTo(Level.OFF.toString()));

        final String trace = loggingManagementService.toggleConfiguredLoggersTraceOff();
        assertThat(trace, equalTo(Level.TRACE.toString()));
    }

    @Test
    public void getRootLoggerLogLevelWhenNoLoggerExists() // defend anyway
    {
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getRootLogger()).thenReturn(null);

        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final String logLevel = loggingManagementService.getRootLoggerLogLevel();
        assertThat(logLevel, equalTo(StringUtils.EMPTY));
    }

    @Test
    public void getRootLoggerLogLevel()
    {
        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final String logLevel = loggingManagementService.getRootLoggerLogLevel();
        assertThat(logLevel, equalTo(Level.OFF.toString()));
    }

    @Test
    public void adjustRootLoggerLogLevelWhenNoLoggerExists() // defend anyway
    {
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getRootLogger()).thenReturn(null);

        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final String level = loggingManagementService.adjustRootLoggerLogLevel(Level.WARN.toString());
        assertThat(level, equalTo(StringUtils.EMPTY));
    }

    @Test
    public void adjustRootLoggerLogLevel()
    {
        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final String level = loggingManagementService.adjustRootLoggerLogLevel(Level.WARN.toString());
        assertThat(level, equalTo(Level.WARN.toString()));
    }

    @Test
    public void adjustIndividualLoggerLogLevel()
    {
        final LoggingManagementService loggingManagementService = new LoggingManagementService();
        final boolean adjusted = loggingManagementService.adjustIndividualLoggerLogLevel("any old bollocks, it will create the logger...", Level.INFO.toString());
        assertThat(adjusted, equalTo(true));
    }
}