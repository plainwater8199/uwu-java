package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerPathParameterSchemaItem {
    private String type;
    @JsonProperty("$ref")
    private String ref;
    private String originalRef;
    private String format;
}
