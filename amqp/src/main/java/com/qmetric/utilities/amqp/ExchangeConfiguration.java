package com.qmetric.utilities.amqp;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ExchangeConfiguration
{
    @NotEmpty
    @JsonProperty
    private String exchange;

    @JsonProperty
    private String routingKey;

    public String getExchange()
    {
        return exchange;
    }

    public String getRoutingKey()
    {
        return routingKey;
    }
}
