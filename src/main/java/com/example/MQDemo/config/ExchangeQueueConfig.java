package com.example.MQDemo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeQueueConfig {
    public static final String EXCHANGE_NAME = "demo_exchange";
    public static final String QUEUE_NAME = "demo_queue";
    public static final String ROUTING_KEY = "routing_key";

    @Bean("demoExchange")
    public DirectExchange demoExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean("demoQueue")
    public Queue demoQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    public Binding demoQueueBindingDemoExchange(@Qualifier("demoQueue") Queue demoQueue,
                                                @Qualifier("demoExchange") DirectExchange demoExchange) {
        return BindingBuilder.bind(demoQueue).to(demoExchange).with(ROUTING_KEY);
    }
}
