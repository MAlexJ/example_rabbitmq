### Fanout exchange

The RabbitMQ fanout exchange is one of the core exchange types used to broadcast messages to all queues bound to it,
regardless of routing keys.

A fanout exchange in RabbitMQ is used to broadcast messages to all queues bound to it.

It ignores routing keys completely.

#### Summary

```
---------------------------------------------------------------------------------
        Scenario	                                    Result
---------------------------------------------------------------------------------
Fanout exchange + queues + bindings	        All queues receive a copy (broadcast)

Fanout exchange without any queues	        Message is discarded

Routing key used with fanout	            Ignored

Multiple queues bound to same exchange	    Each gets its own copy
```