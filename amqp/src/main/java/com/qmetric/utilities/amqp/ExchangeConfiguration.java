package com.qmetric.utilities.amqp;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExchangeConfiguration
{
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
