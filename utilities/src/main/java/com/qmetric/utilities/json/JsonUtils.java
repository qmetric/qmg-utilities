package com.qmetric.utilities.json;

import com.qmetric.utilities.file.RuntimeIOException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class JsonUtils
{
    /**
     * Serialize an object to a JSON string.
     *
     * @param obj Object to serialize.
     * @return Json string.
     * @throws RuntimeIOException If an exception occurred during serialization. This is an unrecoverable scenario - the same string will continue to
     * fail serialization.
     */
    public String serializeToJson(final Object obj)
    {
        try
        {
            final StringWriter sw = new StringWriter();
            final ObjectMapper mapper = new ObjectMapper();
            final MappingJsonFactory jsonFactory = new MappingJsonFactory();
            final JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);

            mapper.writeValue(jsonGenerator, obj);
            sw.close();

            return sw.getBuffer().toString();
        }
        catch (final IOException e)
        {
            throw new RuntimeIOException(e);
        }
    }
}