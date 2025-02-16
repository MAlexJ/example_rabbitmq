### RabbitMQ with Spring Boot

The jsonMessageConverter() method creates a message converter bean to generate an object converter 
to convert the queue message to JSON.

```
@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
```

link: https://medium.com/@ravinduperera1229/rabbitmq-with-spring-boot-1935ed42da6a