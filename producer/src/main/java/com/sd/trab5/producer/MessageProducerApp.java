package com.sd.trab5.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sd.trab5.common.config.RabbitMQConfig;
import com.sd.trab5.common.model.MessagePayload;
import com.sd.trab5.common.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

public class MessageProducerApp {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducerApp.class);

    // ANSI Colors for console presentation
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BOLD = "\u001B[1m";

    public static void main(String[] args) {
        logger.info("Iniciando Gerador de Mensagens (Producer)...");

        double ratePerSec = Double.parseDouble(System.getenv().getOrDefault("RATE_PER_SEC", "6.0"));
        if (ratePerSec < 5.0) {
            logger.warn("Taxa configurada ({}) abaixo do requisito mínimo de 5 msgs/s. Ajustando para 5.0.", ratePerSec);
            ratePerSec = 5.0;
        }

        // Intervalo de simulação de erro para testar DLQ (em segundos, 0 desabilita)
        int errorIntervalSec = Integer.parseInt(System.getenv().getOrDefault("SIMULATE_ERROR_INTERVAL_SEC", "12"));

        logger.info("Configuração do Produtor: Taxa = {} msgs/s, Intervalo de erro para DLQ = {}s",
                ratePerSec, errorIntervalSec);

        long intervalMs = (long) (1000.0 / ratePerSec);
        MessageGenerator generator = new MessageGenerator();
        ObjectMapper mapper = RabbitMQConfig.getObjectMapper();
        AtomicLong messageCounter = new AtomicLong(0);

        try {
            Connection connection = RabbitMQConfig.createConnection();
            Channel channel = connection.createChannel();

            // Garantir que a topologia (Exchange, Queues, DLQ) está criada
            RabbitMQConfig.setupTopology(channel);

            logger.info("===============================================================");
            logger.info("🚀 Produtor iniciado com sucesso! Gerando mensagens continuamente...");
            logger.info("===============================================================");

            long lastErrorTime = System.currentTimeMillis();

            while (!Thread.currentThread().isInterrupted()) {
                long now = System.currentTimeMillis();
                boolean triggerError = false;

                if (errorIntervalSec > 0 && (now - lastErrorTime) >= (errorIntervalSec * 1000L)) {
                    triggerError = true;
                    lastErrorTime = now;
                }

                MessagePayload payload = generator.generate(triggerError);
                String routingKey = (payload.getType() == MessageType.REVIEW)
                        ? RabbitMQConfig.ROUTING_KEY_REVIEW
                        : RabbitMQConfig.ROUTING_KEY_SUPPORT;

                byte[] messageBytes = mapper.writeValueAsBytes(payload);

                AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                        .contentType("application/json")
                        .contentEncoding("UTF-8")
                        .messageId(payload.getId())
                        .timestamp(java.util.Date.from(payload.getTimestamp()))
                        .build();

                channel.basicPublish(
                        RabbitMQConfig.EXCHANGE_TOPIC,
                        routingKey,
                        props,
                        messageBytes
                );

                long count = messageCounter.incrementAndGet();

                if (payload.isSimulateError()) {
                    System.out.printf("%s%s🚨 [PRODUTOR] #%-5d | [DLQ-ERROR-TEST] | Key: %-15s | ID: %s | %s%s%n",
                            ANSI_BOLD, ANSI_RED, count, routingKey, payload.getId().substring(0, 8),
                            payload.getContent(), ANSI_RESET);
                } else if (payload.getType() == MessageType.REVIEW) {
                    System.out.printf("%s[PRODUTOR] #%-5d | [AVALIAÇÃO] | Key: %-15s | ID: %s | %s%s%n",
                            ANSI_GREEN, count, routingKey, payload.getId().substring(0, 8),
                            payload.getContent(), ANSI_RESET);
                } else {
                    System.out.printf("%s[PRODUTOR] #%-5d | [SUPORTE]   | Key: %-15s | ID: %s | %s%s%n",
                            ANSI_CYAN, count, routingKey, payload.getId().substring(0, 8),
                            payload.getContent(), ANSI_RESET);
                }

                Thread.sleep(intervalMs);
            }

        } catch (InterruptedException e) {
            logger.info("Produtor interrompido com sucesso.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Erro fatal no produtor de mensagens: ", e);
        }
    }
}
