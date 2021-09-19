package com.data.pipeline.datapipeline.mqtt.listner;

import com.data.pipeline.datapipeline.constants.AppConstants;
import com.data.pipeline.datapipeline.mqtt.services.MessageProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttFirstSubscriber implements MqttCallback {

    @Autowired
    private MessageProcessorService messageProcessorService;

    @Override
    public void connectionLost(Throwable throwable) {
        log.warn("Client disconnected from Mqtt Broker : ", throwable.getMessage());
    }

    /**
     * Overriden method to recieve the incoming messages on topics
     *
     * @param topic
     * @param mqttMessage
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.debug("Message Arrived for topic : " + topic);
        messageProcessorService.initMessageProcessing(topic, mqttMessage, AppConstants.CLIENT_O1);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
