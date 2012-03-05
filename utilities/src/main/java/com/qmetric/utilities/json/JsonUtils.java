package com.qmetric.utilities.json;

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
     * @throws IOException Exception occurred during serialization.
     */
    public String serializeToJson(final Object obj) throws IOException
    {
        // Parse questions into JSON, for use by the question branching javascript logic.
        final StringWriter sw = new StringWriter();
        final ObjectMapper mapper = new ObjectMapper();
        final MappingJsonFactory jsonFactory = new MappingJsonFactory();
        final JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);

        mapper.writeValue(jsonGenerator, obj);
        sw.close();

        return sw.getBuffer().toString();
    }
}