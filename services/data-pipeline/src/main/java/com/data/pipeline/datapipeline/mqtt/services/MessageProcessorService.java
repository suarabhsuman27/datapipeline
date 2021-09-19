package com.data.pipeline.datapipeline.mqtt.services;

import com.data.pipeline.datapipeline.common.models.TelemetryDataModel;
import com.data.pipeline.datapipeline.constants.AppConstants;
import com.data.pipeline.datapipeline.kafka.producer.KafkaMessageSender;
import com.data.pipeline.datapipeline.mqtt.messages.FirstClientMessage;
import com.data.pipeline.datapipeline.mqtt.messages.SecondClientMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * {@link MessageProcessorService} contains method to process the incoming message
 * from different messages for different client on different topics
 */
@Component
@Slf4j
public class MessageProcessorService {

    @Value("${mqtt.client.topic.client.first}")
    private String firstClientTopic;

    @Value("${mqtt.client.topic.client.second}")
    private String secondClientTopic;

    @Value("${kafka.producer.topic}")
    private String kafkaTopic;

    @Autowired
    private KafkaMessageSender kafkaSender;

    /**
     * Method to start the process of incoming messages from different clients on different topics
     *
     * @param topic
     * @param message
     */
    public void initMessageProcessing(String topic, MqttMessage message, String client) {
        log.debug("MessageProcessorService : initMessageProcessing :  started");
        try {
            Gson gson = new Gson();
            TelemetryDataModel telemetryData = null;
            if (topic.equals(firstClientTopic)) {
                FirstClientMessage firstClientMsg = gson.fromJson(new String(message.getPayload()),
                        FirstClientMessage.class);
                telemetryData = TelemetryDataModel.builder().temperature(firstClientMsg.getTmp())
                        .speed(firstClientMsg.getSpeed()).latitude(firstClientMsg.getLatit())
                        .longitude(firstClientMsg.getLongitude())
                        .clientId(AppConstants.CLIENT_O1)
                        .build();
                log.info("First Client Message : " + firstClientMsg);
            } else if (topic.equals(secondClientTopic)) {
                SecondClientMessage secClientMsg = gson.fromJson(new String(message.getPayload()),
                        SecondClientMessage.class);
                telemetryData = TelemetryDataModel.builder().temperature(secClientMsg.getTemp())
                        .speed(secClientMsg.getSpeed()).latitude(secClientMsg.getLat())
                        .longitude(secClientMsg.getLongi())
                        .clientId(AppConstants.CLIENT_O2)
                        .build();
                log.info("Second Client Message : " + secClientMsg);
            } else {
                log.warn("Message from un-attendant topic");
            }
            kafkaSender.sendMessage(kafkaTopic, telemetryData);
        } catch (Exception exception) {
            log.error("Failed to parse the message :", exception.getStackTrace());
        }
        log.debug("MessageProcessorService : initMessageProcessing :  completed");
    }
}
