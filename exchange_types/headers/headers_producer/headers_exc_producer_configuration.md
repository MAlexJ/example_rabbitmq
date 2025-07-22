### Headers Exchange

Header Exchange routes messages based on message header attributes instead of routing keys.
In Spring Boot, configuring and using a Headers Exchange works similarly to direct, topic, or fanout exchanges,
but with headers as routing criteria.

#### Configuration

Setup configuration for:

1. ConnectionFactory
2. MessageConverter (for JSON)
3. RabbitTemplate
4. Autoconfiguration: HeadersExchange

#### 4.1. Exchange Declaration – optional (only if producer wants to ensure exchange exists).

You can declare the exchange in the producer app using:

` 
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("my-headers-exchange");
    }
`

#### 4.2 Routing ignored

```
@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendDocument(Document document) {
        rabbitTemplate.convertAndSend(
            "my-headers-exchange",
            "",  // routing key is ignored in headers exchange
            document,
            message -> {
                message.getMessageProperties().setHeader("format", "json");
                message.getMessageProperties().setHeader("documentType", document.type().toString());
                return message;
            }
        );
    }
}
```