package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerPathParameterSchema {
    @JsonProperty("$ref")
    private String ref;
    private String originalRef;
    private String format;
    private String type;
    private SwaggerPathParameterSchemaItem items;
}
