### Spring configuration

1. Add the configuration to `application.yaml` file

link: https://stackoverflow.com/questions/37557622/is-it-possible-to-set-prefetch-count-on-rabbitlistener

Configuration app properties for Rabbit jars:

```
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitProperties { 

....
    public abstract static class AmqpContainer extends BaseContainer {
    	/**
		 * Acknowledge mode of container.
		 */
		private AcknowledgeMode acknowledgeMode;

		/**
		 * Maximum number of unacknowledged messages that can be outstanding at each
		 * consumer.
		 */
		private Integer prefetch;
}
```

The Maximum number of unacknowledged messages that can be outstanding at each consumer.

configuration:

```
spring:
  rabbitmq:
    listener:
      direct:
        prefetch: 1
```

or

```
spring:
  rabbitmq:
    listener:
      simple:
        prefetch: 1
```

### Project configuration

###### Add properties to `.env` file:

```
RABBITMQ_HOST=cow.rmq2.cloudamqp.com
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=......
RABBITMQ_PASSWORD=........
RABBITMQ_VIRTUAL_HOST=......
```

###### Provider

* https://www.cloudamqp.com (Message Queues in the Cloud)