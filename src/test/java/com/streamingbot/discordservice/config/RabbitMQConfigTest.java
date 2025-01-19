package com.streamingbot.discordservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    private RabbitMQConfig rabbitMQConfig;
    private static final String TEST_QUEUE = "test.user.deletion.queue";
    private static final String TEST_EXCHANGE = "test.user.events.exchange";
    private static final String TEST_ROUTING_KEY = "test.user.deletion.key";

    @BeforeEach
    void setUp() {
        rabbitMQConfig = new RabbitMQConfig();
        ReflectionTestUtils.setField(rabbitMQConfig, "userDeletionQueue", TEST_QUEUE);
        ReflectionTestUtils.setField(rabbitMQConfig, "userEventsExchange", TEST_EXCHANGE);
        ReflectionTestUtils.setField(rabbitMQConfig, "userDeletionRoutingKey", TEST_ROUTING_KEY);
    }

    @Test
    void userDeletionQueue_ShouldCreateQueueWithDLQ() {
        Queue queue = rabbitMQConfig.userDeletionQueue();
        
        assertNotNull(queue);
        assertEquals(TEST_QUEUE, queue.getName());
        assertTrue(queue.isDurable());
        assertEquals("", queue.getArguments().get("x-dead-letter-exchange"));
        assertEquals(TEST_QUEUE + ".dlq", queue.getArguments().get("x-dead-letter-routing-key"));
    }

    @Test
    void userDeletionDlq_ShouldCreateDLQueue() {
        Queue dlq = rabbitMQConfig.userDeletionDlq();
        
        assertNotNull(dlq);
        assertEquals(TEST_QUEUE + ".dlq", dlq.getName());
        assertTrue(dlq.isDurable());
    }

    @Test
    void userEventsExchange_ShouldCreateTopicExchange() {
        TopicExchange exchange = rabbitMQConfig.userEventsExchange();
        
        assertNotNull(exchange);
        assertEquals(TEST_EXCHANGE, exchange.getName());
    }

    @Test
    void userDeletionBinding_ShouldCreateBinding() {
        Queue queue = rabbitMQConfig.userDeletionQueue();
        TopicExchange exchange = rabbitMQConfig.userEventsExchange();
        
        Binding binding = rabbitMQConfig.userDeletionBinding(queue, exchange);
        
        assertNotNull(binding);
        assertEquals(TEST_ROUTING_KEY, binding.getRoutingKey());
    }

    @Test
    void jsonMessageConverter_ShouldCreateConverter() {
        MessageConverter converter = rabbitMQConfig.jsonMessageConverter();
        
        assertNotNull(converter);
        assertTrue(converter instanceof Jackson2JsonMessageConverter);
    }
} 