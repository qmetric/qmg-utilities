package com.qmetric.utilities.amqp;

import com.fasterxml.jackson.annotation.JsonProperty;
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
