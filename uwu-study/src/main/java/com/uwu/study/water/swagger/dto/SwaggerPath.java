package com.uwu.study.water.swagger.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SwaggerPath {
    private String[] tags;
    private String summary;
    private String operationId;
    private String[] consumes;
    private String[] produces;
    private List<SwaggerPathParameter> parameters;
    private Map<String,SwaggerPathResponse> responses;
    private Object security;
    private String description;


    private String type;
    private String name;
    private String in;
    private String[] required;
    private Map<String,SwaggerPathProperties> properties;
    private String title;


}
