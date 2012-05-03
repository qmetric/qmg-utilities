package com.qmetric.utilities.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.SerializerProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class EnumSerializerTest
{
    private static final int EXPECTED_NUMBER_OF_PROPERTIES_TO_SERIALIZE = 2;

    private JsonGenerator mockJsonGenerator;

    private SerializerProvider mockSerializerProvider;

    private EnumSerializer enumSerializer;

    @Before
    public void context()
    {
        mockJsonGenerator = mock(JsonGenerator.class);
        mockSerializerProvider = mock(SerializerProvider.class);

        enumSerializer = new EnumSerializer();
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionIfNotEnum() throws IOException
    {
        enumSerializer.serialize("not en enum, this is a string", mockJsonGenerator, mockSerializerProvider);
    }

    @Test
    public void shouldCorrectlySerializeNameMethod() throws IOException
    {
        enumSerializer.serialize(TestEnum.A, mockJsonGenerator, mockSerializerProvider);

        final Map<String, Object> properties = capturePropertiesToSerialize();

        assertThat((String) properties.get("name"), equalTo("A"));
    }

    @Test
    public void shouldCorrectlySerializeGetterMethods() throws IOException
    {
        enumSerializer.serialize(TestEnum.A, mockJsonGenerator, mockSerializerProvider);

        final Map<String, Object> properties = capturePropertiesToSerialize();

        assertThat((String) properties.get("something"), equalTo("getter value"));
    }

    @Test
    public void shouldIgnorePrivateGetterMethods() throws IOException
    {
        enumSerializer.serialize(TestEnum.A, mockJsonGenerator, mockSerializerProvider);

        final Map<String, Object> properties = capturePropertiesToSerialize();

        assertThat(properties.get("somethingFromPrivateMethod"), nullValue());
    }

    @Test
    public void shouldIgnoreNonGetterMethods() throws IOException
    {
        enumSerializer.serialize(TestEnum.A, mockJsonGenerator, mockSerializerProvider);

        final Map<String, Object> properties = capturePropertiesToSerialize();

        assertThat(properties.get("notAGetterMethod"), nullValue());
    }

    @Test
    public void shouldIgnoreGetterMethodsWithIgnoreAnnotation() throws IOException
    {
        enumSerializer.serialize(TestEnum.A, mockJsonGenerator, mockSerializerProvider);

        final Map<String, Object> properties = capturePropertiesToSerialize();

        assertThat(properties.get("somethingElse"), nullValue());
    }

    @Test
    public void shouldIgnoreInheritedGetterMethods() throws IOException
    {
        enumSerializer.serialize(TestEnum.A, mockJsonGenerator, mockSerializerProvider);

        final Map<String, Object> properties = capturePropertiesToSerialize();

        assertThat(properties.size(), equalTo(EXPECTED_NUMBER_OF_PROPERTIES_TO_SERIALIZE));
    }

    private Map<String, Object> capturePropertiesToSerialize() throws IOException
    {
        final ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
        verify(mockJsonGenerator).writeObject(captor.capture());

        //noinspection unchecked
        return (Map<String, Object>) captor.getValue();
    }

    public static enum TestEnum
    {
        A;

        public String getSomething()
        {
            return "getter value";
        }

        @JsonIgnore
        public String getSomethingElse()
        {
            return "getter value";
        }

        public String notAGetterMethod()
        {
            return "value";
        }

        private String getSomethingFromPrivateMethod()
        {
            return "getter value from private method";
        }
    }
}