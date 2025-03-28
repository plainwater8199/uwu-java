package com.uwu.study.water.swagger.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SwaggerDefinition {
    private String type;
    private String[] required;
    private Map<String,SwaggerDefinitionProperties> properties;
    private String title;
    private String description;
    private Object items;
}
