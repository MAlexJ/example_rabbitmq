### Producer configuration

Setup configuration for:

1. ConnectionFactory
2. MessageConverter (for JSON)
3. RabbitTemplate
4. Autoconfiguration: Exchange, Routing Key ...

#### 4.1. Exchange Declaration – optional (only if producer wants to ensure exchange exists).

You can declare the exchange in the producer app using:

` 
@Bean
public TopicExchange topicExchange() {
      return new TopicExchange("auditExchange");
}
`

Or rely on RabbitMQ pre-created exchanges.

#### 4.2.2 Routing Key – producer MUST specify a routing key when sending.

* Producer uses exact routing keys (like custom_configuration_producer_routing_key.user)

* Consumer’s binding must use a pattern to catch those messages:ø

  `custom_configuration_producer_routing_key.*`
  OR
  `custom_configuration_producer_routing_key.#`

Example:
`rabbitTemplate.convertAndSend("auditExchange", "audit.events.users.signup", message);`

#### 4.3. Queues / Bindings – producer does not care about queues or binding patterns.

note: It's the consumer's responsibility.
