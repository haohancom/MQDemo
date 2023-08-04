package com.example.MQDemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static com.example.MQDemo.config.ExchangeQueueConfig.MAIN_EXCHANGE_NAME;
import static com.example.MQDemo.config.ExchangeQueueConfig.ROUTING_KEY;

@Slf4j
@Controller
@RequestMapping("/msg")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String WRONG_EXCHANGE_NAME = "wrong_exchange_name";
    private static final String WRONG_ROUTING_KEY = "wrong_routing_key";

    @GetMapping(path = "/send/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("send msg: {}", message);
        rabbitTemplate.convertAndSend(WRONG_EXCHANGE_NAME, ROUTING_KEY, message.getBytes(), new CorrelationData(UUID.randomUUID().toString()));
        rabbitTemplate.convertAndSend(MAIN_EXCHANGE_NAME, ROUTING_KEY, message.getBytes(), new CorrelationData(UUID.randomUUID().toString()));
        rabbitTemplate.convertAndSend(MAIN_EXCHANGE_NAME, WRONG_ROUTING_KEY, message.getBytes(), new CorrelationData(UUID.randomUUID().toString()));
    }
}
