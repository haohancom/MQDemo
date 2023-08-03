package com.example.MQDemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Component
public class CallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = Optional.ofNullable(correlationData)
                .map(CorrelationData::getId)
                .orElse("");
        if (ack) {
            log.info("exchange has received msg whose id is: {}", id);
        } else {
            log.error("exchange has not received msg whose id is: {}, caused by: {}", id, cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("Msg {} has been returned by exchange {}, caused by : {}, routing key is {}", returnedMessage.getMessage(), returnedMessage.getExchange(), returnedMessage.getReplyText(), returnedMessage.getRoutingKey());
    }
}
