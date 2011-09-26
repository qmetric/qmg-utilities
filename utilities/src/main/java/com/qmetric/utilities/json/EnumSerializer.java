package com.qmetric.utilities.json;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.dozer.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class EnumSerializer extends JsonSerializer<Object>
{
    private static final String ENUM_NAME_METHOD = "name";

    @Override public void serialize(final Object o, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException
    {
        final Map<String, Object> serializedProperties = new HashMap<String, Object>();

        serializeEnumNameMethod(o, serializedProperties);

        serializeEnumGetters(o, serializedProperties);

        writeSerializedProperties(jsonGenerator, serializedProperties);
    }

    private void serializeEnumNameMethod(final Object o, final Map<String, Object> map) throws IOException
    {
        final Method nameMethod = ReflectionUtils.getMethod(o, ENUM_NAME_METHOD);
        Validate.notNull(nameMethod, "No enum to serialize!");

        final String name = (String) ReflectionUtils.invoke(nameMethod, o, null);

        map.put(ENUM_NAME_METHOD, name);
    }

    private void writeSerializedProperties(final JsonGenerator jsonGenerator, final Map<String, Object> map) throws IOException
    {
        jsonGenerator.writeObject(map);
    }

    private void serializeEnumGetters(final Object o, final Map<String, Object> map) throws IOException
    {
        for (final PropertyDescriptor descriptor : ReflectionUtils.getPropertyDescriptors(o.getClass()))
        {
            final Method method = descriptor.getReadMethod();

            if (ArrayUtils.contains(o.getClass().getDeclaredMethods(), method) && method.getAnnotation(JsonIgnore.class) == null)
            {
                final Object obj = ReflectionUtils.invoke(method, o, null);

                map.put(descriptor.getName(), obj);
            }
        }
    }
}