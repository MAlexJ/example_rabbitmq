package com.malex.async_remote_call.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@RequiredArgsConstructor
public class ReplyQueueConfiguration {

  // 1. reply queue name
  @Value("${custom.rabbitmq.queue.reply}")
  private String replyQueue;

  // 2. configuration/creation reply queue
  @Bean
  public Queue replyQueue() {
    return QueueBuilder.durable(replyQueue).build();
  }

  // 3. configuration template: RabbitTemplateConfiguration.rabbitTemplate(...)

  // 4. Configuration reply listener container
  @Bean
  public SimpleMessageListenerContainer replyListenerContainer(
      ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueues(replyQueue());
    container.setMessageListener(rabbitTemplate);
    return container;
  }
}
