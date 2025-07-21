#### Consumer configuration

1. ListenerContainerFactory
2. MessageConverter (for JSON)
3. Autoconfiguration: Queue, Routing Key, Binding ...

Consumer responsibility:

#### 3.1 Exchange Declaration – recommended.

The consumer should declare the exchange to ensure it's created, unless guaranteed to exist.

#### 3.2 Queue Declaration – required for message reception.

Consumers declare their own queues (dedicated or shared).

#### 3.3 Binding with Pattern (Routing Key Pattern) – required.

Binding connects the queue to the exchange using a pattern like:

  ```
  BindingBuilder.bind(queue)
      .to(topicExchange)
      .with("audit.events.#");
  ```

#### 3.4. Routing Key Pattern – defines what messages the queue will receive.

Example:

```
  QueueA binding: audit.events.#
  QueueB binding: audit.events.orders.*
```

#### JSON MessageConverter

When using Jackson2JsonMessageConverter, Spring AMQP (and its DefaultJackson2JavaTypeMapper)
includes the fully qualified class name in the message header:

__TypeId__: com.malexj.producer.Producer$Event

```
@Bean
public DefaultJackson2JavaTypeMapper typeMapper() {
    // Map remote type to local type
    var typeMapper = new DefaultJackson2JavaTypeMapper();
    Map<String, Class<?>> idClassMapping = new HashMap<>();
    idClassMapping.put("com.malexj.producer.Producer$Event", Consumer.Event.class);
    
    typeMapper.setIdClassMapping(idClassMapping);
    return typeMapper;
}
```


