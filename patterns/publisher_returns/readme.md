### Publisher Returns

link: https://www.rabbitmq.com/tutorials/tutorial-seven-java

#### Info

When a producer sends a message, RabbitMQ gives no guarantee by default that:

The broker received the message.
The message was routed to a queue.

To improve message delivery reliability, RabbitMQ supports two key mechanisms:

* Publisher Confirms (ACK from broker)
* Publisher Returns (Routing failure feedback)

#### Publisher Returns (Routing failure feedback)

* Triggered when the message was not routed to any queue (e.g., no queue is bound with matching routing key).
* The exchange receives it, but drops it if mandatory=false (default).
* If mandatory=true, and no queue matches, the broker returns the message to the producer.

Use case: Detect bad routing keys or missing queue bindings.

#### Spring Boot Configuration

* Enable Publisher Returns

`spring.rabbitmq.publisher-returns: true`

Enables return callbacks when routing fails.

Requires that you also set mandatory = true on your RabbitTemplate:

`rabbitTemplate.setMandatory(true);`

```
@Bean
public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMandatory(true); // Required for returns
    template.setConfirmCallback((correlationData, ack, cause) -> {
        if (ack) {
            System.out.println("Message delivered successfully");
        } else {
            System.err.println("Message delivery failed: " + cause);
        }
    });

    template.setReturnsCallback(returned -> {
        System.err.println("Returned message: " + returned.getMessage());
        System.err.println("Reason: " + returned.getReplyText());
    });

    return template;
}
```

#### Summary Table

```
-----------------------------------------------------------------------------------------------------------------------
Feature               Trigger	                Purpose	                                 Requires
-----------------------------------------------------------------------------------------------------------------------
Publisher Confirm	Broker receives 	    Ensure message                 publisher-confirm-type: correlated
                    the message             reaches RabbitMQ	

Publisher Return	Message not routed 	    Detect routing problems	       publisher-returns: true + mandatory = true
                    to any queue
```