package com.qmetric.utilities.amqp;

import com.google.common.io.Resources;
import com.yammer.dropwizard.config.ConfigurationException;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class BrokerConfigurationTest
{
    private final Validator validator = new Validator();
    private final ConfigurationFactory<BrokerConfiguration> configurationFactory = ConfigurationFactory.forClass(BrokerConfiguration.class, validator);

    private File file;

    @Before
    public void setUp() throws URISyntaxException
    {
        file = new File(Resources.getResource("broker.yml").toURI());
    }

    @Test
    public void shouldLoadConfigurationFromYaml() throws IOException, ConfigurationException
    {
        BrokerConfiguration configuration = configurationFactory.build(file);

        assertNotNull(configuration);
        assertThat(configuration.getHost(), equalTo("test-host"));
        assertThat(configuration.getPort(), equalTo(2));
        assertThat(configuration.getUsername(), equalTo("test-user"));
        assertThat(configuration.getPassword(), equalTo("test-password"));
        assertThat(configuration.getVirtualHost(), equalTo("test"));
    }
}
