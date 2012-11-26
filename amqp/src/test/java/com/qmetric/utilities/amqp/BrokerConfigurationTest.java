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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class BrokerConfigurationTest
{
    private final Validator validator = new Validator();
    private final ConfigurationFactory<BrokerConfiguration> configurationFactory = ConfigurationFactory.forClass(BrokerConfiguration.class, validator);

    private File fileWithHost, fileWithAddresses;

    @Before
    public void setUp() throws URISyntaxException
    {
        fileWithHost = new File(Resources.getResource("broker.yml").toURI());
        fileWithAddresses = new File(Resources.getResource("broker-addresses.yml").toURI());
    }

    @Test
    public void shouldLoadConfigurationWithHostFromYaml() throws IOException, ConfigurationException
    {
        BrokerConfiguration configuration = configurationFactory.build(fileWithHost);

        assertNotNull(configuration);
        assertThat(configuration.getHost(), equalTo("test-host"));
        assertNull(configuration.getAddresses());
        assertThat(configuration.getPort(), equalTo(2));
        assertThat(configuration.getUsername(), equalTo("test-user"));
        assertThat(configuration.getPassword(), equalTo("test-password"));
        assertThat(configuration.getVirtualHost(), equalTo("test"));
        assertThat(configuration.getHeartbeat(), equalTo(10));
    }

    @Test
    public void shouldLoadConfigurationWithAddressesFromYaml() throws IOException, ConfigurationException
    {
        BrokerConfiguration configuration = configurationFactory.build(fileWithAddresses);

        assertNotNull(configuration);
        assertNull(configuration.getHost());
        assertThat(configuration.getAddresses(), equalTo("test-host, test-host2"));
        assertThat(configuration.getPort(), equalTo(2));
        assertThat(configuration.getUsername(), equalTo("test-user"));
        assertThat(configuration.getPassword(), equalTo("test-password"));
        assertThat(configuration.getVirtualHost(), equalTo("test"));
        assertThat(configuration.getHeartbeat(), equalTo(0));
    }
}
