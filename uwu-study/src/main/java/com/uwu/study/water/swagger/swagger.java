package com.uwu.study.water.swagger;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.uwu.study.water.swagger.dto.*;
import com.uwu.study.water.swagger.tableDto.ReqItem;
import com.uwu.study.water.swagger.tableDto.RespItem;
import com.uwu.study.water.swagger.tableDto.TableInfo;
import org.apache.poi.xwpf.usermodel.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class swagger {

    public static void main(String[] args) {
        System.out.println("Hello, Swagger!");
        String json = getInfo("/Users/wangpeng/water/chatbot.json");
        String saveFile = "/Users/wangpeng/water/request-chatbot.docx";

        ObjectMapper mapper = new ObjectMapper();
        try {
            SwaggerDTO swaggerDTO = mapper.readValue(json, SwaggerDTO.class);

            Map<String, SwaggerDefinition> definitions = swaggerDTO.getDefinitions();
            List<TableInfo> tableInfos = new ArrayList<>();

            Map<String, Map<String, SwaggerPath>> paths = swaggerDTO.getPaths();
            for (Map.Entry<String, Map<String, SwaggerPath>> entry : paths.entrySet()) {
                //1、接口地址
                String uri = entry.getKey();
                Map<String, SwaggerPath> pathMap = entry.getValue();
                TableInfo tableInfo = new TableInfo();
                tableInfo.setUri(uri);
                if(!pathMap.isEmpty()){
                    Map.Entry<String, SwaggerPath> firstEntry = pathMap.entrySet().iterator().next();
                    //2、接口请求方法
                    String type = firstEntry.getKey();
                    SwaggerPath value = firstEntry.getValue();
                    //3、接口描述
                    String summary = value.getSummary();
                    tableInfo.setUriName(summary);
                    tableInfo.setType(type);

                    List<SwaggerPathParameter> parameters = value.getParameters();
                    if(parameters != null && !parameters.isEmpty()){
                        List<ReqItem> reqs = new ArrayList<>();
                        for (SwaggerPathParameter parameter : parameters) {

                            ReqItem reqItem = new ReqItem();
                            String parameterName = parameter.getName();
                            String parameterDescription = parameter.getDescription();
                            boolean isRequired = parameter.isRequired();
                            String parameterType  = parameter.getType();
                            reqItem.setName(parameterName);
                            reqItem.setType(parameterType);
                            reqItem.setIsNeed(isRequired ? "true" : "false");
                            reqItem.setDesc(parameterDescription);
                            reqs.add(reqItem);




                            SwaggerPathParameterSchema schema = parameter.getSchema();
                            if(schema!= null) {

                                ReqItem reqItemSchema = new ReqItem();
                                reqItemSchema.setName(schema.getOriginalRef());
                                reqs.add(reqItemSchema);


                                SwaggerDefinition swaggerDefinition = definitions.get(schema.getOriginalRef());
                                if (swaggerDefinition != null) {
                                    Map<String, SwaggerDefinitionProperties> properties = swaggerDefinition.getProperties();
                                    if (!properties.isEmpty()) {
                                        for (Map.Entry<String, SwaggerDefinitionProperties> propertyEntry : properties.entrySet()) {
                                            String propertyName = propertyEntry.getKey();
                                            SwaggerDefinitionProperties propertyValue = propertyEntry.getValue();
                                            ReqItem reqItemProperty = new ReqItem();
                                            reqItemProperty.setName(propertyName);
                                            reqItemProperty.setType(propertyValue.getType());
                                            reqItemProperty.setIsNeed("--");
                                            reqItemProperty.setDesc(propertyValue.getDescription());
                                            reqs.add(reqItemProperty);
                                            SwaggerDefinitionPropertiesItems items = propertyValue.getItems();
                                            if(items != null){
                                                String originalRef = items.getOriginalRef();
                                                SwaggerDefinition swaggerDefinition1 = definitions.get(originalRef);
                                                if(swaggerDefinition1 != null){
                                                    Map<String, SwaggerDefinitionProperties> properties1 = swaggerDefinition1.getProperties();
                                                    ReqItem reqItemProperty1 = new ReqItem();
                                                    reqItemProperty1.setName(originalRef);
                                                    reqs.add(reqItemProperty1);
                                                    if(!properties1.isEmpty()){

                                                        for (Map.Entry<String, SwaggerDefinitionProperties> propertyEntry1 : properties1.entrySet()) {
                                                            ReqItem reqItemProperty11 = new ReqItem();
                                                            String propertyEntry1key = propertyEntry1.getKey();
                                                            reqItemProperty11.setName(propertyEntry1key);
                                                            reqItemProperty11.setType(propertyEntry1.getValue().getType());
                                                            reqItemProperty11.setIsNeed("--");
                                                            reqItemProperty11.setDesc(propertyEntry1.getValue().getDescription());
                                                            reqs.add(reqItemProperty11);
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }


                        }
                        tableInfo.setReqs(reqs);
                    }
                    Map<String, SwaggerPathResponse> responses = value.getResponses();
                    if(!responses.isEmpty()){
                        SwaggerPathResponse swaggerPathResponse = responses.get("200");
                        SwaggerPathResponseSchema schema = swaggerPathResponse.getSchema();
                        if(schema!= null){
                            String ref = schema.getOriginalRef();
                            SwaggerDefinition swaggerDefinition = definitions.get(ref);
                            if(swaggerDefinition != null){
                                Map<String, SwaggerDefinitionProperties> properties = swaggerDefinition.getProperties();
                                if(!properties.isEmpty()){
                                    List<RespItem> resps = new ArrayList<>();
                                    for (Map.Entry<String, SwaggerDefinitionProperties> propertyEntry : properties.entrySet()) {
                                        RespItem respItem = new RespItem();
                                        respItem.setName(propertyEntry.getKey());
                                        SwaggerDefinitionProperties propertyValue = propertyEntry.getValue();
                                        respItem.setDesc(propertyValue.getDescription());
                                        respItem.setIsNeed("--");
                                        respItem.setType(propertyValue.getType());
                                        resps.add(respItem);
                                    }
                                    tableInfo.setResqs(resps);
                                }
                            }
                        }

                    }

                    }
                System.out.println(tableInfo.getUri());
                tableInfos.add(tableInfo);
            }



            Map<String, List<RespItem>> respMap = new HashMap<>();
            if(definitions != null && !definitions.isEmpty()){


                for(Map.Entry<String, SwaggerDefinition> entry : definitions.entrySet()) {
                    String key = entry.getKey();
                    SwaggerDefinition value = entry.getValue();
                    Map<String, SwaggerDefinitionProperties> properties = value.getProperties();
                    List<RespItem> resps = new ArrayList<>();
                    if(!properties.isEmpty()){

                        RespItem respItem;
                        for (Map.Entry<String, SwaggerDefinitionProperties> propertyEntry : properties.entrySet()) {
                            respItem = new RespItem();
                            String propertyName = propertyEntry.getKey();
                            SwaggerDefinitionProperties value1 = propertyEntry.getValue();
                            respItem.setName(propertyName);
                            respItem.setType(value1.getType());
                            respItem.setIsNeed("--");
                            respItem.setDesc(value1.getDescription());
                            resps.add(respItem);
                        }
                    }
                    respMap.put(key, resps);
                }
            }


            createTable(tableInfos,respMap,saveFile);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static void createTable2(Map<String, List<RespItem>> respMap,String saveFile) {
        try (XWPFDocument document = new XWPFDocument()) {
            for (Map.Entry<String,  List<RespItem>> propertyEntry : respMap.entrySet()) {


                // 添加表名
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("接口: " + propertyEntry.getKey());
                run.setBold(true);
                run.addBreak();

                // 创建表格
                XWPFTable table = document.createTable();
                XWPFTableRow headerRow0 = table.getRow(0);
                headerRow0.getCell(0).setText(propertyEntry.getKey());
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");

//                // 添加表头
//                XWPFTableRow headerRow = table.createRow();
//                headerRow.getCell(0).setText("序号");
//                headerRow.getCell(1).setText("参数名称");
//                headerRow.getCell(2).setText("类型");
//                headerRow.getCell(3).setText("是否必填");
//                headerRow.getCell(4).setText("描述");


                if(propertyEntry.getValue() != null && !propertyEntry.getValue().isEmpty()){
                    // 添加字段信息
                    for (int i = 0; i < propertyEntry.getValue().size(); i++) {
                        RespItem column = propertyEntry.getValue().get(i);
                        XWPFTableRow row = table.createRow();
                        row.getCell(0).setText(String.valueOf(i + 1));
                        row.getCell(1).setText(column.getName());
                        row.getCell(2).setText(column.getType());
                        row.getCell(3).setText(column.getIsNeed());
                        row.getCell(4).setText(column.getDesc());
                    }
                }

                // 添加空行分隔
                document.createParagraph();
            }

            // 保存文档
            try (FileOutputStream out = new FileOutputStream(saveFile)) {
                document.write(out);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void createTable(List<TableInfo> tableInfos,Map<String, List<RespItem>> respMap,String saveFile) {
        try (XWPFDocument document = new XWPFDocument()) {
            for (TableInfo tableInfo : tableInfos) {


                // 添加表名
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("接口: " + tableInfo.getUri());
                run.setBold(true);
                run.addBreak();

                // 创建表格
                XWPFTable table = document.createTable();
                XWPFTableRow headerRow0 = table.getRow(0);
                headerRow0.getCell(0).setText("接口");
                headerRow0.addNewTableCell().setText(tableInfo.getUri());
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");

                XWPFTableRow headerRow1 = table.createRow();
                headerRow1.getCell(0).setText("接口描述");
                headerRow1.getCell(1).setText(tableInfo.getUriName());
                headerRow1.getCell(2).setText("");
                headerRow1.getCell(3).setText("");
                headerRow1.getCell(4).setText("");

                XWPFTableRow headerRow2 = table.createRow();
                headerRow2.getCell(0).setText("请求方式");
                headerRow2.getCell(1).setText(tableInfo.getType());
                headerRow2.getCell(2).setText("");
                headerRow2.getCell(3).setText("");
                headerRow2.getCell(4).setText("");



                // 添加表头
                XWPFTableRow headerRow = table.createRow();
                headerRow.getCell(0).setText("序号");
                headerRow.getCell(1).setText("参数名称");
                headerRow.getCell(2).setText("类型");
                headerRow.getCell(3).setText("是否必填");
                headerRow.getCell(4).setText("描述");


                if(tableInfo.getReqs() != null && !tableInfo.getReqs().isEmpty()){
                    XWPFTableRow rowReq = table.createRow();
                    rowReq.getCell(0).setText("REQUEST");
                    rowReq.getCell(1).setText("");
                    rowReq.getCell(2).setText("");
                    rowReq.getCell(3).setText("");
                    rowReq.getCell(4).setText("");
                    // 添加字段信息
                    for (int i = 0; i < tableInfo.getReqs().size(); i++) {
                        ReqItem column = tableInfo.getReqs().get(i);
                        XWPFTableRow row = table.createRow();
                        row.getCell(0).setText(String.valueOf(i + 1));
                        row.getCell(1).setText(column.getName());
                        row.getCell(2).setText(column.getType());
                        row.getCell(3).setText(column.getIsNeed());
                        row.getCell(4).setText(column.getDesc());
                    }
                }

                if(tableInfo.getResqs() != null && !tableInfo.getResqs().isEmpty()){
                    XWPFTableRow rowResp = table.createRow();
                    rowResp.getCell(0).setText("RESPONSE");
                    rowResp.getCell(1).setText("");
                    rowResp.getCell(2).setText("");
                    rowResp.getCell(3).setText("");
                    rowResp.getCell(4).setText("");
                    // 添加字段信息
                    for (int i = 0; i < tableInfo.getResqs().size(); i++) {
                        RespItem column = tableInfo.getResqs().get(i);
                        XWPFTableRow row = table.createRow();
                        row.getCell(0).setText(String.valueOf(i + 1));
                        row.getCell(1).setText(column.getName());
                        row.getCell(2).setText(column.getType());
                        row.getCell(3).setText(column.getIsNeed());
                        row.getCell(4).setText(column.getDesc());
                    }
                }


                // 添加空行分隔
                document.createParagraph();
            }

            for (Map.Entry<String,  List<RespItem>> propertyEntry : respMap.entrySet()) {


                // 添加表名
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("接口: " + propertyEntry.getKey());
                run.setBold(true);
                run.addBreak();

                // 创建表格
                XWPFTable table = document.createTable();
                XWPFTableRow headerRow0 = table.getRow(0);
                headerRow0.getCell(0).setText(propertyEntry.getKey());
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");

//                // 添加表头
//                XWPFTableRow headerRow = table.createRow();
//                headerRow.getCell(0).setText("序号");
//                headerRow.getCell(1).setText("参数名称");
//                headerRow.getCell(2).setText("类型");
//                headerRow.getCell(3).setText("是否必填");
//                headerRow.getCell(4).setText("描述");


                if(propertyEntry.getValue() != null && !propertyEntry.getValue().isEmpty()){
                    // 添加字段信息
                    for (int i = 0; i < propertyEntry.getValue().size(); i++) {
                        RespItem column = propertyEntry.getValue().get(i);
                        XWPFTableRow row = table.createRow();
                        row.getCell(0).setText(String.valueOf(i + 1));
                        row.getCell(1).setText(column.getName());
                        row.getCell(2).setText(column.getType());
                        row.getCell(3).setText(column.getIsNeed());
                        row.getCell(4).setText(column.getDesc());
                    }
                }

                // 添加空行分隔
                document.createParagraph();
            }



            // 保存文档
            try (FileOutputStream out = new FileOutputStream(saveFile)) {
                document.write(out);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private static String getInfo(String filePath) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return sb.toString();
    }
}
