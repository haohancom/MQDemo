package com.example.MQDemo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.MQDemo.config.ExchangeQueueConfig.WARNING_QUEUE_NAME;

@Component
@Slf4j
public class WarningConsumer {
    @RabbitListener(queues = WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("Non-routable msg: {}", msg);
    }
}
