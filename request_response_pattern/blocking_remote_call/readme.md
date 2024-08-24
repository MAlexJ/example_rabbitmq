### Request/Reply Messaging

link: https://docs.spring.io/spring-amqp/reference/amqp/request-reply.html

###### Message Correlation With A Reply Queue

When using a fixed reply queue (other than amq.rabbitmq.reply-to), you must provide correlation data so that replies
can be correlated to requests.
See RabbitMQ Remote Procedure Call (RPC).
By default, the standard correlationId property is used to hold the correlation data.
However, if you wish to use a custom property to hold correlation data, you can set the correlation-key attribute
on the <rabbit-template/>.
Explicitly setting the attribute to correlationId is the same as omitting the attribute.
The client and server must use the same header for correlation data.

Reply Listener Container
When using RabbitMQ versions prior to 3.4.0, a new temporary queue is used for each reply.
However, a single reply queue can be configured on the template

```
  @Bean
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(msgConv());
        
        // >>> Reply queue configuration
        rabbitTemplate.setReplyAddress(replyQueue().getName());
        rabbitTemplate.setReplyTimeout(60000);
        // >>>
        
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(replyQueue());
        container.setMessageListener(amqpTemplate());
        return container;
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("my.reply.queue");
    }
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