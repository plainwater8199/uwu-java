package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerDefinitionPropertiesAdditionalProperties {
    @JsonProperty("$ref")
    private String ref;
    private String originalRef;
    private String type;
    private String format;
}
