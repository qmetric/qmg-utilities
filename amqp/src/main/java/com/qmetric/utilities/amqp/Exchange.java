package com.qmetric.utilities.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class Exchange
{
    private final RabbitTemplate rabbitTemplate;

    public Exchange(final ConnectionFactory connectionFactory, final ExchangeConfiguration exchangeConfiguration)
    {
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchangeConfiguration.getName());
        rabbitTemplate.setRoutingKey(exchangeConfiguration.getRoutingKey());
    }

    public void send(final Message message)
    {
        rabbitTemplate.send(message);
    }

    public void send(final String routingKey, final Message message)
    {
        rabbitTemplate.send(routingKey, message);
    }
}
