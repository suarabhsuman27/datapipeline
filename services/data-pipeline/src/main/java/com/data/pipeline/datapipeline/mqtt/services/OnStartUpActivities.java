package com.data.pipeline.datapipeline.mqtt.services;

import com.data.pipeline.datapipeline.mqtt.clients.MqttSubscriberClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class OnStartUpActivities {

    @Autowired
    private MqttSubscriberClient subscriber;

    /**
     *
     * @param event
     */
    @EventListener
    public void doSubscriberOnStartUp(ContextRefreshedEvent event){
        log.debug("OnStartUpActivities : doSubscriberOnStartUp: started");
        subscriber.doSubscribeOnStartUp();
        log.debug("OnStartUpActivities : doSubscriberOnStartUp: completed");
    }

}
