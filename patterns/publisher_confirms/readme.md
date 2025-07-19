### Publisher Confirms

link: https://www.rabbitmq.com/tutorials/tutorial-seven-java

#### Info

When a producer sends a message, RabbitMQ gives no guarantee by default that:

The broker received the message.
The message was routed to a queue.

To improve message delivery reliability, RabbitMQ supports two key mechanisms:

* Publisher Confirms (ACK from broker)
* Publisher Returns (Routing failure feedback)

#### Publisher Confirms (ACK from broker)

Confirms that RabbitMQ broker has received the message into the exchange.
It does NOT confirm whether it was routed to a queue — only that it was accepted by the exchange.

Use case: Detect and retry if the broker is down or unreachable.

#### Spring Boot Configuration

* Enable Publisher Confirms

`spring.rabbitmq.publisher-confirm-type: correlated`

Enables confirm callbacks with correlation IDs.
Lets you handle success/failure for each message.
Values:

* none
* simple
* correlated

Always prefer correlated for per-message tracking.

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