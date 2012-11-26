package com.qmetric.utilities.amqp;

import com.yammer.dropwizard.validation.ValidationMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BrokerConfiguration
{
    @JsonProperty
    private String host;

    @JsonProperty
    private String addresses;

    @Min(1) @Max(65535) @JsonProperty
    private int port = 5672;

    @NotEmpty @JsonProperty
    private String username;

    @NotEmpty @JsonProperty
    private String password;

    @NotEmpty @JsonProperty
    private String virtualHost;

    @JsonProperty
    private int heartbeat;

    @ValidationMethod(message = "Either a host field or an addresses field must be present")
    public boolean isHostOrAddressPresent()
    {
        return notEmpty(host) || notEmpty(addresses);
    }

    private boolean notEmpty(String s)
    {
        return s != null && s.trim().length() > 0;
    }

    public String getHost()
    {
        return host;
    }

    public String getAddresses()
    {
        return addresses;
    }

    public int getPort()
    {
        return port;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getVirtualHost()
    {
        return virtualHost;
    }

    public int getHeartbeat()
    {
        return heartbeat;
    }
}
