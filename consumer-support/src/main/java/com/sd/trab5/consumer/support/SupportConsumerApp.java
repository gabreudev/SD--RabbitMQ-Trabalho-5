package com.sd.trab5.consumer.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.sd.trab5.common.ai.SmileClassifier;
import com.sd.trab5.common.ai.TrainingData;
import com.sd.trab5.common.config.RabbitMQConfig;
import com.sd.trab5.common.model.MessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SupportConsumerApp {

    private static final Logger logger = LoggerFactory.getLogger(SupportConsumerApp.class);

    // ANSI Colors for console
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_BOLD = "\u001B[1m";

    public static void main(String[] args) {
        logger.info("Iniciando Consumidor 2 - Classificação de Solicitações de Suporte (Smile AI)...");

        // Tempo de processamento artificial para simular consumo mais lento que a taxa de chegada
        int processingDelayMs = Integer.parseInt(System.getenv().getOrDefault("PROCESSING_DELAY_MS", "350"));
        logger.info("Delay de processamento por mensagem configurado para: {}ms", processingDelayMs);

        // 1. Treinar modelo Smile AI de Classificação de Chamados Técnicos
        logger.info("Treinando IA embarcada (Smile DiscreteNaiveBayes - Suporte Técnico)...");
        SmileClassifier classifier = new SmileClassifier(
                TrainingData.getSupportClasses(),
                TrainingData.getSupportTrainingSamples()
        );
        logger.info("IA treinada com sucesso! Classes: {}, Vocabulário extraído: {} termos.",
                classifier.getClasses(), classifier.getVocabularySize());

        ObjectMapper mapper = RabbitMQConfig.getObjectMapper();

        try {
            Connection connection = RabbitMQConfig.createConnection();
            Channel channel = connection.createChannel();

            // Garantir topologia
            RabbitMQConfig.setupTopology(channel);

            // Prefetch = 1 para garantir controle de taxa e acúmulo visível na fila
            channel.basicQos(1);

            logger.info("=========================================================================");
            logger.info("🛠️  Consumidor 2 [SUPORTE] aguardando mensagens na fila '{}'...", RabbitMQConfig.QUEUE_SUPPORT);
            logger.info("=========================================================================");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                String body = new String(delivery.getBody(), StandardCharsets.UTF_8);

                try {
                    MessagePayload payload = mapper.readValue(body, MessagePayload.class);

                    // Teste de simulação de erro para Dead Letter Queue (DLQ)
                    if (payload.isSimulateError() || (payload.getContent() != null && payload.getContent().contains("[TESTE_DLQ_ERRO"))) {
                        System.out.printf("%s%s⚠️  [CONSUMIDOR 2 - SUPORTE] ERRO PROCESSUAL DETECTADO! ID: %s | Rejeitando mensagem com basicNack(requeue=false)... Encaminhada para DLQ!%s%n",
                                ANSI_BOLD, ANSI_RED, payload.getId().substring(0, 8), ANSI_RESET);

                        // basicNack com requeue=false faz o RabbitMQ enviar a mensagem diretamente para a DLQ via DLX
                        channel.basicNack(deliveryTag, false, false);
                        return;
                    }

                    // Classificação via Smile AI
                    SmileClassifier.ClassificationResult result = classifier.classify(payload.getContent());

                    // Simular tempo de processamento mais lento que o produtor
                    if (processingDelayMs > 0) {
                        Thread.sleep(processingDelayMs);
                    }

                    // Formatar cor por categoria
                    String categoryColor = ANSI_CYAN;
                    if (TrainingData.SUPPORT_ACCESS.equals(result.getLabel())) {
                        categoryColor = ANSI_YELLOW;
                    } else if (TrainingData.SUPPORT_PERFORMANCE.equals(result.getLabel())) {
                        categoryColor = ANSI_RED;
                    }

                    System.out.printf("%s[SUPORTE]%s ID: %s | %s%s%-23s (%.1f%%)%s | Texto: \"%s\" %s[ACK]%s%n",
                            ANSI_CYAN, ANSI_RESET,
                            payload.getId().substring(0, 8),
                            categoryColor, ANSI_BOLD, result.getLabel(), result.getConfidence() * 100.0, ANSI_RESET,
                            payload.getContent(),
                            ANSI_BLUE, ANSI_RESET);

                    // Confirmação manual de consumo (manual ACK)
                    channel.basicAck(deliveryTag, false);

                } catch (Exception e) {
                    logger.error("Erro inesperado ao processar mensagem no Consumidor de Suporte: {}. Encaminhando para DLQ.", e.getMessage());
                    // Em caso de falha no parsing ou processamento, encaminha para DLQ
                    channel.basicNack(deliveryTag, false, false);
                }
            };

            // autoAck = false -> Confirmação manual (manual ACK) obrigatória
            channel.basicConsume(RabbitMQConfig.QUEUE_SUPPORT, false, deliverCallback, consumerTag -> {
                logger.warn("Consumidor cancelado: {}", consumerTag);
            });

            // Manter vivo
            Thread.currentThread().join();

        } catch (InterruptedException e) {
            logger.info("Consumidor de Suporte finalizado.");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.error("Erro fatal no Consumidor de Suporte: ", e);
        }
    }
}
