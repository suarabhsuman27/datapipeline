package com.data.pipeline.datapipeline.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipelineException extends Exception{

    private String errorCode;

    private String errorMessage;
}
