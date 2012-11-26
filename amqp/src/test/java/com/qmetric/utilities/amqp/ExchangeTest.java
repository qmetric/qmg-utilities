package com.qmetric.utilities.amqp;

import org.junit.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExchangeTest
{
    private final ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
    private final ExchangeConfiguration exchangeConfiguration = mock(ExchangeConfiguration.class);

    @Test
    public void shouldReadValuesFromConfigurationOnConstruction()
    {
        new Exchange(connectionFactory, exchangeConfiguration);
        verify(exchangeConfiguration).getExchange();
        verify(exchangeConfiguration).getRoutingKey();
    }
}
