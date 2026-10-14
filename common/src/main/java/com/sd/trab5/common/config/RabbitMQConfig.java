package com.sd.trab5.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    // Exchanges
    public static final String EXCHANGE_TOPIC = "messages.topic";
    public static final String EXCHANGE_DLX = "messages.dlx";

    // Routing Keys
    public static final String ROUTING_KEY_REVIEW = "review.product";
    public static final String ROUTING_KEY_SUPPORT = "support.request";
    public static final String ROUTING_KEY_DLQ = "messages.dlq";

    // Queues
    public static final String QUEUE_REVIEW = "review.queue";
    public static final String QUEUE_SUPPORT = "support.queue";
    public static final String QUEUE_DLQ = "messages.dlq";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static Connection createConnection() throws IOException, TimeoutException, InterruptedException {
        String host = System.getenv().getOrDefault("RABBITMQ_HOST", "localhost");
        int port = Integer.parseInt(System.getenv().getOrDefault("RABBITMQ_PORT", "5672"));
        String username = System.getenv().getOrDefault("RABBITMQ_USERNAME", "guest");
        String password = System.getenv().getOrDefault("RABBITMQ_PASSWORD", "guest");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(5000);

        int maxRetries = 30;
        int delayMs = 2000;

        for (int i = 1; i <= maxRetries; i++) {
            try {
                logger.info("Tentando conectar ao RabbitMQ em {}:{} (tentativa {}/{})...", host, port, i, maxRetries);
                return factory.newConnection();
            } catch (IOException | TimeoutException e) {
                logger.warn("RabbitMQ ainda não está disponível: {}. Aguardando {}ms...", e.getMessage(), delayMs);
                if (i == maxRetries) {
                    throw e;
                }
                Thread.sleep(delayMs);
            }
        }
        throw new IOException("Não foi possível conectar ao RabbitMQ após " + maxRetries + " tentativas.");
    }

    public static void setupTopology(Channel channel) throws IOException {
        logger.info("Declarando exchanges, filas e bindings da topologia...");

        // 1. Declarar Dead Letter Exchange (DLX) e Dead Letter Queue (DLQ)
        channel.exchangeDeclare(EXCHANGE_DLX, BuiltinExchangeType.TOPIC, true);
        channel.queueDeclare(QUEUE_DLQ, true, false, false, null);
        channel.queueBind(QUEUE_DLQ, EXCHANGE_DLX, "#");
        logger.info("DLX '{}' e DLQ '{}' configuradas com sucesso.", EXCHANGE_DLX, QUEUE_DLQ);

        // 2. Declarar Topic Exchange principal
        channel.exchangeDeclare(EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC, true);
        logger.info("Topic Exchange '{}' declarada.", EXCHANGE_TOPIC);

        // 3. Declarar fila de Avaliações com encaminhamento para DLQ em caso de rejeição
        Map<String, Object> reviewQueueArgs = new HashMap<>();
        reviewQueueArgs.put("x-dead-letter-exchange", EXCHANGE_DLX);
        reviewQueueArgs.put("x-dead-letter-routing-key", ROUTING_KEY_DLQ);
        channel.queueDeclare(QUEUE_REVIEW, true, false, false, reviewQueueArgs);
        channel.queueBind(QUEUE_REVIEW, EXCHANGE_TOPIC, ROUTING_KEY_REVIEW);
        logger.info("Fila '{}' vinculada à routing key '{}' com DLQ associada.", QUEUE_REVIEW, ROUTING_KEY_REVIEW);

        // 4. Declarar fila de Suporte com encaminhamento para DLQ em caso de rejeição
        Map<String, Object> supportQueueArgs = new HashMap<>();
        supportQueueArgs.put("x-dead-letter-exchange", EXCHANGE_DLX);
        supportQueueArgs.put("x-dead-letter-routing-key", ROUTING_KEY_DLQ);
        channel.queueDeclare(QUEUE_SUPPORT, true, false, false, supportQueueArgs);
        channel.queueBind(QUEUE_SUPPORT, EXCHANGE_TOPIC, ROUTING_KEY_SUPPORT);
        logger.info("Fila '{}' vinculada à routing key '{}' com DLQ associada.", QUEUE_SUPPORT, ROUTING_KEY_SUPPORT);
    }
}
