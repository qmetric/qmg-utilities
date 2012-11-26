package com.qmetric.utilities.amqp;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ExchangeConfiguration
{
    @NotEmpty
    @JsonProperty
    private String name;

    @JsonProperty
    private String routingKey;

    public String getName()
    {
        return name;
    }

    public String getRoutingKey()
    {
        return routingKey;
    }
}
