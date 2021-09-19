package com.data.pipeline.datapipeline.mqtt.clients;

import com.data.pipeline.datapipeline.constants.AppConstants;
import com.data.pipeline.datapipeline.exceptions.PipelineException;
import com.data.pipeline.datapipeline.mqtt.listner.MqttFirstSubscriber;
import com.data.pipeline.datapipeline.mqtt.listner.MqttSecondSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class MqttSubscriberClient {

    private MqttClient mqttClient;

    @Value("${mqtt.broker.ip}")
    private String brokerIp;

    @Value("${mqtt.broker.port}")
    private String brokerPort;

    @Value("${mqtt.client.topic.client.first}")
    private String firstClientTopic;

    @Value("${mqtt.client.topic.client.second}")
    private String secondClientTopic;

    @Autowired
    private MqttFirstSubscriber firstSubscriber;

    @Autowired
    private MqttSecondSubscriber secondSubscriber;

    /**
     * Method to subscribe on
     */
    public void doSubscribeOnStartUp() {
        log.debug("MqttSubscriberClient : doSubscribeOnStartUp :  started");
        try {
            MqttClient firstClient = getMqttClient("client-01");
            firstClient.subscribe(firstClientTopic);
            firstClient.setCallback(firstSubscriber);

            MqttClient secondClient = getMqttClient("client-02");
            secondClient.subscribe(secondClientTopic);
            secondClient.setCallback(secondSubscriber);
        } catch (PipelineException pipelineException) {
            log.error("Failed to Subscriber on topic during start up : ", pipelineException.getErrorMessage());
        } catch (MqttException mqttException) {
            log.error("Failed to subscriber to the topic : ", mqttException.getMessage());
        } catch (Exception exception) {
            log.error("Failed to subscriber to the topic : ", exception.getMessage());
        }
        log.debug("MqttSubscriberClient : doSubscribeOnStartUp :  completed");
    }

    /**
     * Method to create the client if not created before and reconnect if it's created and disconnected
     *
     * @return
     */
    private MqttClient getMqttClient(String client) throws PipelineException {
        log.debug("MqttSubscriberClient : getMqttClient :  started");
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(Boolean.TRUE);
            options.setCleanSession(Boolean.TRUE);
            options.setKeepAliveInterval(60);
            options.setAutomaticReconnect(Boolean.TRUE);
            if (Objects.isNull(mqttClient)) {
                mqttClient = new MqttClient("tcp://" + brokerIp + ":" + brokerPort, client);
                mqttClient.connect(options);
            } else if (Objects.nonNull(mqttClient) && !mqttClient.isConnected()) {
                mqttClient.connect(options);
            }
            log.info(client + " connected to broker successfully");
        } catch (MqttException mqttException) {
            log.error("Failed to connected to the broker : ", mqttException.getMessage());
            throw new PipelineException(AppConstants.FAILED_CODE, AppConstants.FAILED_MSG);
        } catch (Exception exception) {
            log.error("Failed to connected to the broker : ", exception.getMessage());
        }
        log.debug("MqttSubscriberClient : getMqttClient :  started");
        return mqttClient;
    }
}
