### Consumer Prefetch

link: https://www.rabbitmq.com/docs/consumer-prefetch

AMQP 0-9-1 specifies the basic.qos method to make it possible to limit the number of unacknowledged messages on a
channel (or connection) when consuming (aka "prefetch count").

Unfortunately, the channel is not the ideal scope for this since a single channel may consume from multiple queues,
the channel and queue(s) need to coordinate with each other for every message sent
to ensure they don't go over the limit.

This is slow on a single machine, and very slow when consuming across a cluster.

Furthermore, for many uses, it is simply more natural to specify a prefetch count that applies to each consumer.

###### Single Consumer

The following basic example in Java will receive
a maximum of `10` unacknowledged messages at once:

```
Channel channel = ...;
Consumer consumer = ...;
channel.basicQos(10); // Per consumer limit
channel.basicConsume("my-queue", false, consumer);
```

A value of 0 is treated as infinite,
allowing any number of unacknowledged messages.

```
Channel channel = ...;
Consumer consumer = ...;
channel.basicQos(0); // No limit for this consumer
channel.basicConsume("my-queue", false, consumer);
```

###### Independent Consumers

This example starts two consumers on the same channel,
each of which will independently receive a maximum of 10
unacknowledged messages at once:

```
Channel channel = ...;
Consumer consumer1 = ...;
Consumer consumer2 = ...;
channel.basicQos(10); // Per consumer limit
channel.basicConsume("my-queue1", false, consumer1);
channel.basicConsume("my-queue2", false, consumer2);
```