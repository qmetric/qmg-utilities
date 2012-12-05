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
        assertThat(configuration.getName(), equalTo("test"));
        assertThat(configuration.isAutoDelete(), equalTo(true));
        assertThat(configuration.isDurable(), equalTo(true));
        assertThat(configuration.isExclusive(), equalTo(true));
        assertThat(configuration.arguments(), Matchers.<String, Object>hasEntry("x-dead-letter-exchange", "dead-letter"));

        assertThat(configuration.getBindings(), Matchers.hasEntry("exchange1", ""));
        assertThat(configuration.getBindings(), Matchers.hasEntry("exchange2", "test"));
    }
}

