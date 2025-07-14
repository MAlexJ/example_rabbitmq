### Acknowledgement Modes


Systems that use a messaging broker such as RabbitMQ are by definition distributed. Since protocol methods (messages)
sent are not guaranteed to reach the peer or be successfully processed by it, both publishers and consumers need a
mechanism for delivery and processing confirmation. Several messaging protocols supported by RabbitMQ provide such
features. This guide covers the features in AMQP 0-9-1 but the idea is largely the same in other supported protocols.l

Depending on the acknowledgement mode used, RabbitMQ can consider a message to be successfully delivered either
immediately after it is sent out (written to a TCP socket) or when an explicit ("manual") client acknowledgement is
received. Manually sent acknowledgements can be positive or negative and use one of the following protocol methods:

* basic.ack is used for positive acknowledgements
* basic.nack is used for negative acknowledgements (note: this is a RabbitMQ extension to AMQP 0-9-1)
* basic.reject is used for negative acknowledgements but has one limitation compared to basic.nack

link: https://www.rabbitmq.com/docs/confirms


Spring boot manual ack https://gist.github.com/mryf323/04ff1586bcf445bb9ca2c6390fc0db71