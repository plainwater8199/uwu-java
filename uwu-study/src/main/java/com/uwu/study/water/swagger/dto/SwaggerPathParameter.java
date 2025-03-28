package com.uwu.study.water.swagger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwaggerPathParameter {
    private String in;
    private String name;
    private String description;
    private boolean required;
    private String type;
    private String format;
    @JsonProperty("x-example")
    private String xexample;
    private SwaggerPathParameterSchema schema;
    private Object items;
    private String collectionFormat;
    @JsonProperty("enum")
    private Object enuma;
}
