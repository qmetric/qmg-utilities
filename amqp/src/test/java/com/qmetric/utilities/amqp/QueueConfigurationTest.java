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

import static org.junit.Assert.assertNotNull;

public class QueueConfigurationTest
{
    private final Validator validator = new Validator();
    private final ConfigurationFactory<QueueConfiguration> configurationFactory = ConfigurationFactory.forClass(QueueConfiguration.class, validator);

    private File file;

    @Before
    public void setUp() throws URISyntaxException
    {
        file = new File(Resources.getResource("queue.yml").toURI());
    }

    @Test
    public void shouldLoadConfigurationFromYaml() throws IOException, ConfigurationException
    {
        QueueConfiguration configuration = configurationFactory.build(file);

        assertNotNull(configuration);
    }
}

