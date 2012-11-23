QMG Utilities - AMQP
--------------------

This library provides AMQP integration for dropwizard projects.

This is an example drop wizard configuration for an amqp listener and a sender.

```yaml
broker:
  username: mary
  password: mungo
  virtualHost: midge
  host: hole.burrow.rbt
  port: 5672

queue:
  name: carrots
```

To create a listener that will be start and stopped with your dropwizard service, write some code like this in your Service.

```java
@Override protected void initialize(final StorageConfiguration configuration, final Environment environment) throws Exception {

    // This is the listener. It can be a MessageListener or a ChannelAwareMessageListener.
    final MyListener myListener = new MyListener();

    // This is another listener.
    final AnotherListener = new AnotherListener();

    // This is the connectionFactory configured to connect to the AMQP broker.
    final CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(configuration.getBroker().getHost());
    connectionFactory.setPort(configuration.getBroker().getPort());
    connectionFactory.setVirtualHost(configuration.getBroker().getVirtualHost());
    connectionFactory.setUsername(configuration.getBroker().getUsername());
    connectionFactory.setPassword(configuration.getBroker().getPassword());

    // This line registers the listener as a managed object and adds a health check.
    new AmqpListener(environment, connectionFactory, myListener, AcknowledgeMode.MANUAL, false, configuration.getQueue());

    // This registers another listener with the same broker.
    new AmqpListener(environment, connectionFactory, anotherListener, AcknowledgeMode.MANUAL, false, configuration.getQueue());
}
```