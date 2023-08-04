package com.example.MQDemo.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExchangeQueueConfig {
    public static final String MAIN_EXCHANGE_NAME = "main_exchange";
    public static final String MAIN_QUEUE_NAME = "main_queue";
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";
    public static final String BACKUP_QUEUE_NAME = "backup_queue";
    public static final String WARNING_QUEUE_NAME = "warning_queue";
    public static final String ROUTING_KEY = "routing_key";

    @Bean("mainExchange")
    public DirectExchange mainExchange() {
        return ExchangeBuilder
                .directExchange(MAIN_EXCHANGE_NAME)
                .alternate(BACKUP_EXCHANGE_NAME)
                .build();
    }

    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean("mainQueue")
    public Queue mainQueue() {
        return QueueBuilder.durable(MAIN_QUEUE_NAME).build();
    }

    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }
    @Bean
    public Binding mainQueueBindingMainExchange(@Qualifier("mainQueue") Queue mainQueue,
                                                @Qualifier("mainExchange") DirectExchange mainExchange) {
        return BindingBuilder.bind(mainQueue).to(mainExchange).with(ROUTING_KEY);
    }
    @Bean
    public Binding backupQueueBindingBackupExchange(@Qualifier("backupQueue") Queue backupQueue,
                                                @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }
    @Bean
    public Binding warningQueueBindingBackupExchange(@Qualifier("warningQueue") Queue warningQueue,
                                                    @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
