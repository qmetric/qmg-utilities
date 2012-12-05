package com.qmetric.utilities.amqp;

import com.google.common.base.Joiner;
import com.rabbitmq.client.Channel;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.lifecycle.Managed;
import com.yammer.dropwizard.logging.Log;
import com.yammer.metrics.core.HealthCheck;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import java.io.IOException;
import java.util.Map;

public class AmqpListener extends HealthCheck implements Managed
{
    private final Log log = Log.forClass(AmqpListener.class);

    private final ConnectionFactory connectionFactory;

    private final SimpleMessageListenerContainer messageListenerContainer;

    private final String[] queueNames;

    public AmqpListener(final Environment environment, final ConnectionFactory connectionFactory, final Object listener, final AcknowledgeMode acknowledgeMode,
                        final boolean requeueIfRejected, final QueueConfiguration... queues)
    {
        super(String.format("AmqpListener (%s)", Joiner.on(',').join(queueNames(queues))));
        this.connectionFactory = connectionFactory;
        this.queueNames = queueNames(queues);

        for (QueueConfiguration queueConfiguration : queues)
        {
            declareQueue(queueConfiguration);
        }

        this.messageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        messageListenerContainer.setQueueNames(queueNames);
        messageListenerContainer.setMessageListener(listener);
        messageListenerContainer.setAcknowledgeMode(acknowledgeMode);
        messageListenerContainer.setDefaultRequeueRejected(requeueIfRejected);

        environment.manage(this);
        environment.addHealthCheck(this);
    }

    private static String[] queueNames(final QueueConfiguration[] queueConfigurations)
    {
        final String[] names = new String[queueConfigurations.length];

        for (int i = 0; i < queueConfigurations.length; i++)
        {
            names[i] = queueConfigurations[i].getName();
        }

        return names;
    }

    private void declareQueue(final QueueConfiguration queue)
    {
        Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        try
        {
            if (!queue.isPassive())
            {
                channel.queueDeclare(queue.getName(), queue.isDurable(), queue.isExclusive(), queue.isAutoDelete(), queue.arguments());
            }
            else
            {
                channel.queueDeclarePassive(queue.getName());
            }

            if (queue.getBindings() != null && !queue.getBindings().isEmpty())
            {
                for (Map.Entry<String, String> entry : queue.getBindings().entrySet())
                {
                    channel.queueBind(queue.getName(), entry.getKey(), entry.getValue());
                }
            }
        }
        catch (IOException e)
        {
            log.error(e, "Unable to declare queue {}", queue);
        }
        finally
        {
            try
            {
                channel.close();
            }
            catch (IOException e)
            {
                log.error(e, "Unable to close channel");
            }

            connection.close();
        }
    }

    @Override public void start() throws Exception
    {
        messageListenerContainer.start();
        log.info("Message listener started");
    }

    @Override public void stop() throws Exception
    {
        messageListenerContainer.stop();
        log.info("Message listener stopped");
    }

    @Override protected Result check() throws Exception
    {
        for (String queue : queueNames)
        {
            Result result = checkQueue(queue);

            if (!result.isHealthy())
            {
                return result;
            }
        }

        return HealthCheck.Result.healthy();
    }

    private Result checkQueue(String queue)
    {
        Connection connection = null;
        Channel channel = null;

        try
        {
            connection = connectionFactory.createConnection();
            channel = connection.createChannel(false);
            channel.queueDeclarePassive(queue);
        }
        catch (Throwable ignored) // Need to catch all errors!
        {
            return HealthCheck.Result.unhealthy("Unable to connect to AMQP, using queueDeclare on %s", queue);
        }
        finally
        {
            try
            {
                if (channel != null)
                {
                    channel.close();
                }
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch (IOException ignored) // This is is a valid response if AMQP down.
            {
            }
        }

        return HealthCheck.Result.healthy();
    }
}

