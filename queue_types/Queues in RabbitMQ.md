### Queues in RabbitMQ

official doc: https://www.cloudamqp.com/blog/rabbitmq-queue-types.html

RabbitMQ uses the Mnesia DB(to be replaced with Khepri in RabbitMQ 4.0) to persist a cluster’s metadata. 
To persist messages, it uses a variety of queue types.

#### Queues in RabbitMQ

Queues in RabbitMQ can be durable, temporary, or auto-deleted. 
- Durable queues remain until they are deleted. 
- Temporary queues exist until the server shuts down. 
- Auto-deleted queues are removed when they are no longer in use.

To cater for different use-cases, RabbitMQ offers a variety of queue types:

* Classic Queues
* Quorum Queues
* Stream Queues
* MQTT QoS 0 Queue
