package com.uwu.study.water.swagger.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SwaggerDTO {
    private String swagger;
    private SwaggerInfo info;
    private String host;
    private String basePath;
    private List<SwaggerTags> tags;
    private Map<String, Map<String, SwaggerPath>> paths;
    private Object securityDefinitions;
    private Map<String, SwaggerDefinition> definitions;
}
