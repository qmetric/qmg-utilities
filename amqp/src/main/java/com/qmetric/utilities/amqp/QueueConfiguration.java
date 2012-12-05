package com.qmetric.utilities.amqp;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.HashMap;
import java.util.Map;

public class QueueConfiguration
{
    @NotEmpty @JsonProperty
    private String name;

    @JsonProperty
    private boolean durable = false;

    @JsonProperty
    private boolean exclusive = false;

    @JsonProperty
    private boolean autoDelete = false;

    @JsonProperty
    private String deadLetterExchange = null;

    @JsonProperty
    private boolean passive = false;

    @JsonProperty
    private Map<String, String> bindings = new HashMap<String, String>();

    public String getName()
    {
        return name;
    }

    public boolean isDurable()
    {
        return durable;
    }

    public boolean isExclusive()
    {
        return exclusive;
    }

    public boolean isAutoDelete()
    {
        return autoDelete;
    }

    public boolean isPassive()
    {
        return passive;
    }

    public Map<String, String> getBindings()
    {
        return bindings;
    }

    public Map<String, Object> arguments()
    {
        HashMap<String, Object> arguments = new HashMap<String, Object>();
        if (deadLetterExchange != null)
        {
            arguments.put("x-dead-letter-exchange", deadLetterExchange);
        }

        return arguments;
    }
}
