package com.qmetric.testing.reflection;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;

public final class ReflectionUtility
{
    private static final Logger LOGGER = Logger.getLogger(ReflectionUtility.class);

    private ReflectionUtility()
    {
    }

    public static Object createObjectWithHiddenConstructor(final Class clazz)
    {
        try
        {
            final Constructor c = clazz.getDeclaredConstructor();
            c.setAccessible(true);

            return c.newInstance();
        }
        catch (final Exception e)
        {
            LOGGER.error("unable to create class of type: " + clazz.getName(), e);

            return null;
        }
    }
}