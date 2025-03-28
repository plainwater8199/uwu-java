package com.uwu.study.water.db;


import org.apache.logging.log4j.util.Strings;
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
import java.util.*;
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
        String regex1 = ".*_\\d{10}$";
        String regex2 = ".*_\\d{8}$";
        String regex3 = ".*_\\d{6}$";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet rs = metaData.getTables("misc_tmp", "", "%", new String[]{"TABLE"})) {

                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    System.out.println(tableName);
                    if(!Pattern.matches(regex1, tableName) && !Pattern.matches(regex2, tableName) && !Pattern.matches(regex3, tableName)){
                        tableNameMap.put(tableName, "");
                        tableNames.add(tableName);
                        String ddl = getTableDDL(connection, tableName);

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/wangpeng/water/mysql/ddl.txt", true))) { // 追加模式
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
                writeToWord(tableMetadata, "/Users/wangpeng/water/mysql/ddl.docx");
//                writeToWordName("misc",tableNameMap, "/Users/wangpeng/water/mysql/ddl_name.docx");
            }

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

//                XWPFTableRow headerRow1 = table.getRow(1);
//                headerRow1.getCell(0).setText("数据库用户");
//                headerRow1.addNewTableCell().setText("nce");
//                headerRow1.addNewTableCell().setText("");
//                headerRow1.addNewTableCell().setText("");
//                headerRow1.addNewTableCell().setText("");
//                headerRow1.addNewTableCell().setText("");
//
//                XWPFTableRow headerRow2 = table.getRow(2);
//                headerRow2.getCell(0).setText("主键");
//                headerRow2.addNewTableCell().setText("id");
//                headerRow2.addNewTableCell().setText("");
//                headerRow2.addNewTableCell().setText("");
//                headerRow2.addNewTableCell().setText("");
//                headerRow2.addNewTableCell().setText("");
//
//                XWPFTableRow headerRow3 = table.getRow(3);
//                headerRow3.getCell(0).setText("索引字段");
//                headerRow3.addNewTableCell().setText("");
//                headerRow3.addNewTableCell().setText("");
//                headerRow3.addNewTableCell().setText("");
//                headerRow3.addNewTableCell().setText("");
//                headerRow3.addNewTableCell().setText("");


                XWPFTableRow headerRow1 = table.createRow();
                headerRow1.getCell(0).setText("数据库用户");
                headerRow1.getCell(1).setText("nce");
                headerRow1.getCell(2).setText("");
                headerRow1.getCell(3).setText("");
                headerRow1.getCell(4).setText("");
                headerRow1.getCell(5).setText("");

                XWPFTableRow headerRow2 = table.createRow();
                headerRow2.getCell(0).setText("主键");
                headerRow2.getCell(1).setText("id");
                headerRow2.getCell(2).setText("");
                headerRow2.getCell(3).setText("");
                headerRow2.getCell(4).setText("");
                headerRow2.getCell(5).setText("");

                XWPFTableRow headerRow3 = table.createRow();
                headerRow3.getCell(0).setText("索引字段");
                headerRow3.getCell(1).setText("");
                headerRow3.getCell(2).setText("");
                headerRow3.getCell(3).setText("");
                headerRow3.getCell(4).setText("");
                headerRow3.getCell(5).setText("");



                // 添加表头
                XWPFTableRow headerRow = table.createRow();
                headerRow.getCell(0).setText("序号");
                headerRow.getCell(1).setText("字段名称");
                headerRow.getCell(2).setText("数据类型");
                headerRow.getCell(3).setText("允许为空");
                headerRow.getCell(4).setText("长度");
                headerRow.getCell(5).setText("描述");

                // 添加字段信息
                for (int i = 0; i < columns.size(); i++) {
                    Map<String, String> column = columns.get(i);
                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(String.valueOf(i + 1));
                    row.getCell(1).setText(column.get("name"));
                    row.getCell(2).setText(column.get("type"));
                    row.getCell(3).setText(column.get("nullable"));
                    row.getCell(4).setText(column.get("size"));
                    row.getCell(5).setText(Strings.isEmpty(column.get("description")) ? "数据" : column.get("description"));
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
            try (ResultSet rs = metaData.getColumns("robot_tmp", null, tableName, "%")) {
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

    public Boolean checkIndex(String tableName) {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
//            String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'robot_tmp' AND table_name LIKE 'msg_record%'";
            List<String> strings = Collections.singletonList("msg_record");//jdbcTemplate.queryForList(sql, String.class);
            for (String table_name : strings) {
                try (ResultSet rs = metaData.getIndexInfo("robot_tmp", null, table_name, false, false)) {
                    while (rs.next()) {
                        System.out.println(rs.getString("TABLE_NAME"));
                        String indexName = rs.getString("INDEX_NAME");
                        boolean nonUnique = rs.getBoolean("NON_UNIQUE");
                        // 跳过主键索引
                        if (indexName != null && nonUnique) {
                            // 删除索引
                            String dropSql = "ALTER TABLE " + tableName + " DROP INDEX " + indexName;  //ALTER TABLE msg_record DROP INDEX account_id_3;
                            jdbcTemplate.execute(dropSql);
                            System.out.println("Deleted index: " + indexName);
                        }
                    }
                }
                List<String> sqlList = new ArrayList<>();
                sqlList.add("alter table "+ tableName +" add index (`account_type`)");
                sqlList.add("alter table "+ tableName +" add index (`plan_detail_id`)");
                sqlList.add("alter table "+ tableName +" add index (`creator`)");
                sqlList.add("alter table "+ tableName +" add index (`send_result`)");
                sqlList.add("alter table "+ tableName +" add index (`phone_num`)");
                sqlList.add("alter table "+ tableName +" add index (`message_id`)");
                sqlList.add("alter table "+ tableName +" add index (`account_id`)");

                jdbcTemplate.batchUpdate(sqlList.toArray(new String[0]));
            }




        } catch (SQLException e) {
            System.out.println("--------------");
        }
        return true;

    }
}

