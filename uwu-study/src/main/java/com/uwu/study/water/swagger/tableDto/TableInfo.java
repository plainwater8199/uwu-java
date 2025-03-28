package com.uwu.study.water.swagger.tableDto;

import lombok.Data;

import java.util.List;

@Data
public class TableInfo {
    private String uri;
    private String uriName;
    private String type;
    private List<ReqItem> reqs;
    private List<RespItem> resqs;
}
