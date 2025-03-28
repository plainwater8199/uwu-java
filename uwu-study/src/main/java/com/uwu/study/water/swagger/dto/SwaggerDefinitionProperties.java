package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerDefinitionProperties {
    private String type;
    private Object example;
    private String description;
    private String format;
    private String refType;
    @JsonProperty("$ref")
    private String ref;
    private String originalRef;
    private SwaggerDefinitionPropertiesAdditionalProperties additionalProperties;
    private SwaggerDefinitionPropertiesItems items;
    @JsonProperty("enum")
    private String[] enuma;
}
