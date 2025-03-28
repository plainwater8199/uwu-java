package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerPathResponseSchema {
    private String type;
    private String format;
    private String originalRef;
    @JsonProperty("$ref")
    private String ref;
    private SwaggerPathResponseSchemaItems items;
    private Object additionalProperties;
}
