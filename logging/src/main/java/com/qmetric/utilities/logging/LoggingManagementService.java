package com.qmetric.utilities.logging;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * MBean exposing Log4j management operations.
 */
public final class LoggingManagementService
{
    private List<Logger> configuredForToggle = new ArrayList<Logger>();

    @ManagedOperation(description = "Toggle root logger between WARN and OFF.")
    public String toggleRootLoggerWarnOff()
    {
        final Logger rootLogger = LogManager.getRootLogger();

        return toggleOnOff(rootLogger, Level.WARN);
    }

    @ManagedOperation(description = "Get root logger log level.")
    public String getRootLoggerLogLevel()
    {
        final Logger rootLogger = LogManager.getRootLogger();

        if (rootLogger != null)
        {
            return rootLogger.getLevel().toString();
        }
        else
        {
            return StringUtils.EMPTY;
        }
    }

    @ManagedOperation(description = "Adjust root logger log level.")
    public String adjustRootLoggerLogLevel(final String level)
    {
        final Logger rootLogger = LogManager.getRootLogger();

        if (rootLogger != null)
        {
            return changeLoggerLogLevel(rootLogger, Level.toLevel(level));
        }
        else
        {
            return StringUtils.EMPTY;
        }
    }

    @ManagedOperation(description = "Toggle profiling loggers between TRACE and OFF.")
    public String toggleConfiguredLoggersTraceOff()
    {
        String level = StringUtils.EMPTY;

        for (final Logger logger : configuredForToggle)
        {
            level = toggleOnOff(logger, Level.TRACE);
        }

        return level;
    }

    @ManagedOperation(description = "Adjust individual logger log level.")
    public boolean adjustIndividualLoggerLogLevel(final String loggerName, final String level)
    {
        // if the logger does not exist create it to start logging at that level
        Logger.getLogger(loggerName).setLevel(Level.toLevel(level));

        return true;
    }

    private String toggleOnOff(final Logger logger, final Level onLevel)
    {
        if (logger != null)
        {
            if (Level.OFF.equals(logger.getLevel()))
            {
                return changeLoggerLogLevel(logger, onLevel);
            }
            else
            {
                return changeLoggerLogLevel(logger, Level.OFF);
            }
        }
        else
        {
            return StringUtils.EMPTY;
        }
    }

    private String changeLoggerLogLevel(final Logger logger, final Level level)
    {
        logger.setLevel(level);

        return level.toString();
    }

    public void setConfiguredForToggle(final List<String> configuredForToggle)
    {
        for (final String loggerClass : configuredForToggle)
        {
            final Logger logger = LogManager.exists(loggerClass);
            this.configuredForToggle.add(logger);
        }
    }
}