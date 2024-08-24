### Spring configuration

1. Set prefetch count on @RabbitListener

link: https://stackoverflow.com/questions/37557622/is-it-possible-to-set-prefetch-count-on-rabbitlistener

Declare RabbitListenerContainerFactory bean with prefetch count 10 in some @Configuration class:

```
@Bean
public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchTenRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(rabbitConnectionFactory);
    factory.setPrefetchCount(10);
    return factory;
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