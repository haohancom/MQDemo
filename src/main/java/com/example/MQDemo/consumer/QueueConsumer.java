package com.example.MQDemo.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.MQDemo.config.ExchangeQueueConfig.QUEUE_NAME;

@Slf4j
@Component
public class QueueConsumer {
    @RabbitListener(queues = QUEUE_NAME)
    public void receive(Message message, Channel channel) {
       String msg = new String(message.getBody());
       log.info("received msg: {}", msg);
    }
}
