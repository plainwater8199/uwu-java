package com.upweup.test.water.db;


import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class DatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Map<String,String> tableNameMap = new HashMap<>();
        Map<String, List<Map<String, String>>> tableMetadata = new HashMap<>();

        // 定义正则表达式：以“_”加15位数字结尾
        String regex = ".*_\\d{10}$";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {

                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    if(!Pattern.matches(regex, tableName)){
                        tableNameMap.put(tableName, "");
                        tableNames.add(tableName);
                        String ddl = getTableDDL(connection, tableName);

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/wangpeng/water/ddl.txt", true))) { // 追加模式
                            writer.write(ddl);
                            writer.newLine(); // 添加换行符
                            writer.newLine(); // 添加额外的空行以分隔不同的表
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        List<Map<String, String>> columns = new ArrayList<>();

                        // 获取表的字段信息
                        ResultSet columnsResultSet = metaData.getColumns(null, null, tableName, null);
                        List<String> columnNames = new ArrayList<>();
                        while (columnsResultSet.next()) {
                            if(!columnNames.contains(columnsResultSet.getString("COLUMN_NAME"))){
                                Map<String, String> columnInfo = new HashMap<>();
                                System.out.println("-----------:"+columnsResultSet.getString("COLUMN_NAME"));
                                columnInfo.put("name", columnsResultSet.getString("COLUMN_NAME"));
                                columnInfo.put("type", columnsResultSet.getString("TYPE_NAME"));
                                columnInfo.put("size", String.valueOf(columnsResultSet.getInt("COLUMN_SIZE")));
                                columnInfo.put("nullable", columnsResultSet.getString("IS_NULLABLE"));
                                columnInfo.put("description", columnsResultSet.getString("REMARKS"));

                                columnNames.add(columnsResultSet.getString("COLUMN_NAME"));
                                columns.add(columnInfo);
                            }
                        }
                        tableMetadata.put(tableName, columns);
                    }
                }
            }

            writeToWord(tableMetadata, "/Users/wangpeng/water/ddl.docx");
            writeToWordName("auth",tableNameMap, "/Users/wangpeng/water/ddl_name.docx");



        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableNames;
    }

    private void writeToWordName(String dbName, Map<String, String> tableNameMap, String filePath) throws IOException {
        try (XWPFDocument document = new XWPFDocument()) {
            // 创建表格
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow0 = table.getRow(0);
            headerRow0.getCell(0).setText("库名");
            headerRow0.addNewTableCell().setText("表名称");
            headerRow0.addNewTableCell().setText("功能说明");

            // 添加字段信息
            for (String tableName : tableNameMap.keySet()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(dbName);
                row.getCell(1).setText(tableName);
                row.getCell(2).setText(tableNameMap.get(tableName));
            }

            // 添加空行分隔
            document.createParagraph();

            // 保存文档
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }
        }
    }


    /**
     * 将表结构信息写入 Word 文档
     *
     * @param tableMetadata 表结构信息
     * @param filePath      文件保存路径
     */
    public static void writeToWord(Map<String, List<Map<String, String>>> tableMetadata, String filePath) throws IOException {
        try (XWPFDocument document = new XWPFDocument()) {
            for (Map.Entry<String, List<Map<String, String>>> entry : tableMetadata.entrySet()) {
                String tableName = entry.getKey();
                List<Map<String, String>> columns = entry.getValue();

                // 添加表名
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText("表名: " + tableName);
                run.setBold(true);
                run.addBreak();

                // 创建表格
                XWPFTable table = document.createTable();
                XWPFTableRow headerRow0 = table.getRow(0);
                headerRow0.getCell(0).setText("表名");
                headerRow0.addNewTableCell().setText(tableName);
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");
                headerRow0.addNewTableCell().setText("");


                // 添加表头
                XWPFTableRow headerRow = table.createRow();
                headerRow.getCell(0).setText("序号");
                headerRow.getCell(1).setText("字段名称");
                headerRow.getCell(2).setText("数据类型");
                headerRow.getCell(3).setText("允许为空");
                headerRow.getCell(4).setText("长度");
                headerRow.getCell(5).setText("描述");

                // 添加字段信息
                for (int i = 1; i < columns.size(); i++) {
                    Map<String, String> column = columns.get(i);
                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(String.valueOf(i + 1));
                    row.getCell(1).setText(column.get("name"));
                    row.getCell(2).setText(column.get("type"));
                    row.getCell(3).setText(column.get("nullable"));
                    row.getCell(4).setText(column.get("size"));
                    row.getCell(5).setText(column.get("description"));
                }

                // 添加空行分隔
                document.createParagraph();
            }

            // 保存文档
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                document.write(out);
            }
        }
    }






    private String getTableDDL(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("-------------------"+tableName);
        try{
            ResultSet resultSet = statement.executeQuery("SHOW CREATE TABLE " + tableName);
            if (resultSet.next()) {
                System.out.println(resultSet.getString(2));
                return resultSet.getString(2); // 第二列是建表语句
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "-------------------"+tableName;
    }

    public List<String> getTableColumns(String tableName) {
        List<String> columns = new ArrayList<>();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getColumns(null, null, tableName, "%")) {
                while (rs.next()) {
                    columns.add(rs.getString("COLUMN_NAME") + " " + rs.getString("TYPE_NAME"));
                }
            }
        } catch (SQLException e) {
            System.out.println("--------------");
        }
        return columns;
    }






    public static void main(String[] args) {
        System.out.println("Default Charset: " + Charset.defaultCharset());


        String regex = ".*_\\d{10}$";
        List<String> strings = new ArrayList<>();
        strings.add("hello_123456789012345");
        strings.add("csp_customer_0408189194");
        strings.add("test_12345678901234");
        strings.add("csp_customer_2057111060");
        strings.add("contact_blacklist");
        for (String tableName : strings) {
            if(!Pattern.matches(regex, tableName)){
                System.out.println(tableName);
            }
        }
        String filePath = "/Users/wangpeng/water/ddl.txt"; // 输出文件路径
        String content = "这是一个测试内容。"; // 要写入的内容

        try {
            // 如果需要追加内容，可以使用 StandardOpenOption.APPEND
            Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}

