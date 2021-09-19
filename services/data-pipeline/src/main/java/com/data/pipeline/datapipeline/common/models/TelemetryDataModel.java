package com.data.pipeline.datapipeline.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryDataModel{

    private Float temperature;

    private Float speed;

    private Double latitude;

    private Double longitude;

    private String clientId;
}
