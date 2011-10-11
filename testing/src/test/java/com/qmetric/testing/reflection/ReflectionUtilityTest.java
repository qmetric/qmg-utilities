package com.qmetric.testing.reflection;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public final class ReflectionUtilityTest
{
    @Test
    public void shouldCreateObjectWithDefaultAccessConstructor()
    {
        final ObjectWithDefaultAccessConstructor o = (ObjectWithDefaultAccessConstructor) ReflectionUtility.createObjectWithHiddenConstructor(ObjectWithDefaultAccessConstructor.class);
        assertNotNull("should be able to create object with default access constructor.", o);
    }

    @Test
    public void shouldCreateObjectWithPrivateAccessConstructor()
    {
        final ObjectWithPrivateAccessConstructor o = (ObjectWithPrivateAccessConstructor) ReflectionUtility.createObjectWithHiddenConstructor(ObjectWithPrivateAccessConstructor.class);
        assertNotNull("should be able to create object with private access constructor.", o);
    }

    @Test
    public void canAlsoBeUsedToCreateObjectWithPublicAccessConstructor()
    {
        final Object o = ReflectionUtility.createObjectWithHiddenConstructor(Object.class);
        assertNotNull("should be able to create object with public access constructor.", o);
    }
}