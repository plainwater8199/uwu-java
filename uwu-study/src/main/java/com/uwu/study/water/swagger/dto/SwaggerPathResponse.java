package com.uwu.study.water.swagger.dto;

import lombok.Data;

@Data
public class SwaggerPathResponse {
    private String description;
    private SwaggerPathResponseSchema schema;
}
