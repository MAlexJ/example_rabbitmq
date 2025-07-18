## Pull vs Push Approach – Kafka and RabbitMQ Compared

link: https://www.upsolver.com/blog/kafka-versus-rabbitmq-architecture-performance-use-case

One important difference between Kafka and RabbitMQ is that the first is pull-based, while the other is push-based.

In pull-based systems, the brokers wait for the consumer to ask for data (‘pull’);
if a consumer is late, it can catch up later.

With push-based systems, messages are immediately pushed to any subscribed consumer.
This can cause these two tools to behave differently in some circumstances.

#### Apache Kafka: Pull-based approach

Kafka uses a pull model. 
Consumers request batches of messages from a specific offset. 
Kafka permits long-pooling, which prevents tight loops when there is no message past the offset, 
and aggressively batches messages to support this

A pull model is logical for Kafka because of partitioned data structure. 
Kafka provides message order in a partition with no contending consumers. 
This allows users to leverage the batching of messages for effective message delivery 
and higher throughput.

#### RabbitMQ: Push-based approach

RabbitMQ uses a push model and stops overwhelming consumers through a prefetch limit defined on the consumer.
This can be used for low latency messaging.

The aim of the push model is to distribute messages individually and quickly, to ensure that work is parallelized evenly
and that messages are processed approximately in the order in which they arrived in the queue.
However, this can also cause issues in cases where one or more consumers have ‘died’ 
and are no longer receiving messages.