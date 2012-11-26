package com.qmetric.utilities.amqp;

import com.yammer.dropwizard.config.Environment;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public class Exchange
{
    public Exchange(final Environment environment, final ConnectionFactory connectionFactory, final ExchangeConfiguration exchangeConfiguration)
    {

    }

    public void send(final Message message) {

    }
}
