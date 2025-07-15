### Direct exchange

Direct exchanges route to one or more bound queues, streams or exchanges using
an exact equivalence of a binding's routing key.

For example, a binding (routing) key of "abc" will match "abc" and "abc" only.

#### Requirements

RabbitMQ producer using a Direct Exchange:

1. Define a Queue
2. Define a DirectExchange
3. Bind the Queue to the Exchange using a routingKey

Note:
When a message is published to a direct exchange, it must be routed to at least one queue (matched via a binding-key),
otherwise it's dropped (unless configured with a dead-letter or alternate exchange).
