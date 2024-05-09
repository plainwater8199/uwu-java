package com.uwu.study.ajava8.stream.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataStreamItem {
    private Integer id;
    private String name;
    private Integer age;
    private List<TodoItem> todoList;
    private Boolean success;
}
