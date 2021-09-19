package com.data.pipeline.datapipeline.mqtt.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecondClientMessage {

    private Float temp;

    private Float speed;

    private Double lat;

    private Double longi;
}
