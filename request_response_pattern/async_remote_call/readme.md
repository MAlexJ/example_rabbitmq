### Request/Response Pattern with Spring AMQP

link: https://reflectoring.io/amqp-request-response/

Retrieving An Asynchronous Result Later

Normally, the APIs are fast, and the client expects the response after a few milliseconds or seconds.

But there are cases when the server takes longer to send the response.
It can be because of security policies, high load, or some other long operations on the server-side.
While waiting for the response, the client could work on something different and process the response later.

We can use AsyncRabbitTemplate to achieve this:

```
@Configuration
class ClientConfiguration {

  @Bean
  public AsyncRabbitTemplate asyncRabbitTemplate(
               RabbitTemplate rabbitTemplate){
    return new AsyncRabbitTemplate(rabbitTemplate);
  }
  // Other methods omitted.
}
```