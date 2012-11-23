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

To create a listener

```java
// Create a listener
```