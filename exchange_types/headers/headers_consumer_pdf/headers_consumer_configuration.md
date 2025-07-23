### Consumer configuration

1. ListenerContainerFactory
2. MessageConverter (for JSON)
3. Optional: ConnectionFactoryConfiguration is optional if you use custom configuration oe app prefs
4. Autoconfiguration: Queue and Headers matching
   Note: a routing key is ignored

#### Consumer responsibility:

The consumer should only need to know:

* The queue name it's consuming from.
* The exchange type (to create the correct binding).
* The headers needed for routing.


