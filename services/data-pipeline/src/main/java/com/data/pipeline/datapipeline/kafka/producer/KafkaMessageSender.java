package com.data.pipeline.datapipeline.kafka.producer;

import com.data.pipeline.datapipeline.common.models.TelemetryDataModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * {@link KafkaMessageSender} contains method to produce the message on a topic with help of {@link KafkaTemplate}
 */
@Component
@Slf4j
public class KafkaMessageSender {

    @Autowired
    private KafkaTemplate<String, TelemetryDataModel> kafkaTemplate;

    /**
     * Method to publish {@link TelemetryDataModel} on a topic
     *
     * @param topic
     * @param message
     */
    public void sendMessage(String topic, TelemetryDataModel message) {
        kafkaTemplate.send(topic, message);
        log.info("Kafka Message published successfully");
    }
}
