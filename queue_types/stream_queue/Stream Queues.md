### Stream Queues

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





