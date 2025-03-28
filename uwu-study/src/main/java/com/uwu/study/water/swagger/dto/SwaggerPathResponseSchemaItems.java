package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SwaggerPathResponseSchemaItems {
    private String originalRef;
    @JsonProperty("$ref")
    private String ref;
    private String type;
    private String format;
}
