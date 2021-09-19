package com.data.pipeline.datapipeline.mqtt.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FirstClientMessage {

    private Float tmp;

    private Float speed;

    private Double latit;

    private Double longitude;
}
