### Stream Queues

#### NOTE: CloudAMQP, a hosted RabbitMQ service, does support RabbitMQ Streams.

While older versions or specific configurations might have limitations, RabbitMQ Streams
are a core feature of recent RabbitMQ versions, including those offered by CloudAMQP.

Client applications could talk to a Stream via an AMQP client library, just as they do with queues.
However, it is recommended to use the dedicated Streams protocol plugin and its associate client libraries.
Read on to explore both options.

link: https://www.cloudamqp.com/blog/rabbitmq-streams-and-replay-features-part-1-when-to-use-rabbitmq-streams.html?utm_source=google&utm_medium=cpc&utm_campaign=19669834282&utm_term=rabbitmq%20streams&gad_source=1&gad_campaignid=19669834282&gbraid=0AAAAApKbGlkZcr9krwyZOR6uqN5sYr3jH&gclid=Cj0KCQjw5ubABhDIARIsAHMighaTxGigFHPaB0da9nrFgOJczzrQlV9FHR09KsEZrIfXN14a65f2zEYaApmrEALw_wcB

habr: https://habr.com/ru/companies/otus/articles/653689/

official doc: https://www.cloudamqp.com/blog/rabbitmq-queue-types.html#stream-queues

Stream queues in RabbitMQ are a persistent and replicated data structure that, like traditional queues,
buffer messages from producers for consumers to read.
However, Streams differ from queues in two ways:

* How producers write messages to them
* And how consumers read messages from them

Under the hood, Streams model an append-only log that's immutable.
In this context, this means messages written to a Stream can't be erased, they can only be read.
To read messages from a Stream in RabbitMQ, one or more consumers subscribe to it
and read the same message as many times as they want.

#### Stream Queues use-cases

The use cases where streams shine include:

* Fan-out architectures:
  Where many consumers need to read the same message.

* Replay & time-travel:  
  Where consumers need to reread the same message or start reading from any point in the stream.

* Large Volumes of Messages:
  Streams are great for use cases where large volumes of messages need to be persisted.


* High Throughput:
  RabbitMQ Streams process relatively higher volumes of messages per second.

#### Stream Queues features

Some of the key features supported in Stream Queues are, but not limited to:

* Persistent and Replicated:
  Stream queues always save data to disk and replicate it across nodes, ensuring high data durability.

* Non-Destructive Read:
  Consumers can read the same messages repeatedly without removing them from the queue.

* High Throughput:
  Stream-specific features and a dedicated binary protocol plugin provide optimal performance.

* Inherent Lazy Behaviour:
  Messages are stored directly on disk and do not consume memory until read.

* No Non-Durable or Exclusive Queues:
  Stream queues are always durable and cannot be exclusive or temporary.

* No TTL or Queue Length Limits:
  Instead of TTL and length limits, streams use retention policies to manage data lifecycle.





