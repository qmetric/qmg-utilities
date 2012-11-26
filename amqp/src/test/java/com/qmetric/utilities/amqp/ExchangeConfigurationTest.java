package com.qmetric.utilities.amqp;

import com.google.common.io.Resources;
import com.yammer.dropwizard.config.ConfigurationException;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.validation.Validator;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ExchangeConfigurationTest
{
    private final Validator validator = new Validator();
    private final ConfigurationFactory<ExchangeConfiguration> configurationFactory = ConfigurationFactory.forClass(ExchangeConfiguration.class, validator);

    private File file;

    @Before
    public void setUp() throws URISyntaxException
    {
        file = new File(Resources.getResource("exchange.yml").toURI());
    }

    @Test
    public void shouldLoadConfigurationFromYaml() throws IOException, ConfigurationException
    {
        ExchangeConfiguration configuration = configurationFactory.build(file);

        assertNotNull(configuration);
        assertThat(configuration.getExchange(), equalTo("test"));
        assertThat(configuration.getRoutingKey(), equalTo("route-66"));
    }
}
