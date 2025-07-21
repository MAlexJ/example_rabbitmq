### Topic exchange

A Topic Exchange is a type of RabbitMQ exchange that routes messages to queues based on wildcard
pattern matching against a message’s routing key.

My schema for testing:

```
                                |-----> ALL / * ----> General consumer 
                                |
Producer  ---> Topic exchange --|-----> USERS   ----> User consumer 
                                |
                                |-----> ORDER   ----> Order consumer 
```

#### Routing key binding

Topic exchanges use pattern matching of the message's routing key to the routing (binding) key pattern
used at binding time.

Some segments are populated by specific values, while others are populated by wildcards: `*` for exactly one segment
and `#` for zero or more (including multiple) segments

For routing, the keys are separated into segments by `.`
The routing key can contain a `*` or `#` symbol to match a part of the binding key.

##### The asterisk `(*)` matches one or more keys.

E.g. `sports.*` can represent `sports.tennis`, `sports.cricket` etc.

##### The hash `(#)` symbol matches zero or more keys.

Meanwhile, sports.# can match `sports`, `sports.tennis`, `sports.cricket` etc.

Example Routing Behavior:

```
------------------------------------------------------------------------------------------
  Message Routing Key	              Queue Binding Pattern	        Routed to Queue?
------------------------------------------------------------------------------------------
audit.events.user.signup	              audit.events.#	          ✅ Yes

audit.events.orders.placed	              audit.events.#	          ✅ Yes

audit.events.user	                      audit.events.*	          ✅ Yes

audit.events.user.signup	              audit.events.*	          ❌ No

audit.users	                              audit.events.#	          ❌ No
```