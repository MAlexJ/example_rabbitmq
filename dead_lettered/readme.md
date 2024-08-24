### Dead Letter

What is a Dead Letter Exchange

Messages from a queue can be "dead-lettered", which means these messages are republished to an exchange when any of the
following four events occur.

* The message is negatively acknowledged by a consumer using basic.reject or basic.nack with requeue parameter set to
  false, or
* The message expires due to per-message TTL, or
* The message is dropped because its queue exceeded a length limit, or
* The message is returned more times to a quorum queue than the delivery-limit.

link: https://www.rabbitmq.com/docs/dlx