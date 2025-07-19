### Topic exchange

Topic exchanges use pattern matching of the message's routing key to the routing (binding) key pattern
used at binding time.

For the purpose of routing, the keys are separated into segments by `.`.
Some segments are populated by specific values, while others are populated by wildcards: `*` for exactly one segment
and `#` for zero or more (including multiple) segments

For example:

* A binding (routing key) pattern of `regions.na.cities.*` will match message routing keys `regions.na.cities.toronto`
  and `regions.na.cities.newyork`
  but will not match `regions.na.cities` because `*` is a wildcard that matches exactly one segment

* A binding (routing key) pattern `audit.events.#` will match `audit.events.users.signup`
  and `audit.events.orders.placed` but not `audit.users` because the second segment does not match

* A binding (routing key) pattern of `#` will match any routing key and makes the topic exchange act like a fanout
  for the bindings that use such a pattern

This exchange type is similar to the Direct exchange.
But the difference is that it allows a partial match of the binding key.

The routing key can contain a `*` or `#` symbol to match a part of the binding key.

Eg, `hello.*` matches `hello.world`, `hello.friends` etc.

The asterisk `(*)` matches one or more keys.
Eg. `sports.*` can represent `sports.tennis`, `sports.cricket` etc.

The hash `(#)` symbol matches zero or more keys.
Meanwhile, sports.# can match `sports`, `sports.tennis`, `sports.cricket` etc.