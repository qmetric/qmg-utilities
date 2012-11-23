package com.qmetric.utilities.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQImpl;
import com.yammer.dropwizard.config.Environment;
import com.yammer.metrics.core.HealthCheck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AmqpListenerTest
{

    private final QueueConfiguration queueConfiguration = mock(QueueConfiguration.class);

    private final String queueName = "QUEUE";

    private final Environment environment = mock(Environment.class);

    private final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);

    private final ChannelAwareMessageListener listener = mock(ChannelAwareMessageListener.class);

    private final Connection connection = mock(Connection.class);

    private final Channel channel = mock(Channel.class);

    @Before
    public void setUp() throws IOException
    {
        when(connectionFactory.createConnection()).thenReturn(connection);
        when(connection.createChannel(false)).thenReturn(channel);
        when(queueConfiguration.getName()).thenReturn(queueName);
    }

    @Test
    public void shouldReturnHealthyIfAmqpAvailable() throws Exception
    {
        final AmqpListener amqpListener = new AmqpListener(environment, connectionFactory, listener, AcknowledgeMode.MANUAL, false, queueConfiguration);

        amqpListener.start();
        when(channel.queueDeclarePassive(queueName)).thenReturn(new AMQImpl.Queue.DeclareOk(queueName, 1, 1));

        HealthCheck.Result result = amqpListener.check();
        assertThat(result.isHealthy(), equalTo(true));
        amqpListener.stop();
    }

    @Test
    public void shouldReturnUnHealthyIfAmqpUnavailable() throws Exception
    {
        final AmqpListener amqpListener = new AmqpListener(environment, connectionFactory, listener, AcknowledgeMode.MANUAL, false, queueConfiguration);

        amqpListener.start();
        when(channel.queueDeclarePassive(queueName)).thenThrow(new IOException("BANG!"));

        HealthCheck.Result result = amqpListener.check();
        assertThat(result.isHealthy(), equalTo(false));
        amqpListener.stop();
    }

}
