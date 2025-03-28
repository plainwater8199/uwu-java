package com.uwu.study.water.api;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class SqlColumnData {
    @TableField("COLUMN_NAME")
    private String columnName;
    @TableField("DATA_TYPE")
    private String dataType;
    @TableField("COLUMN_COMMENT")
    private String columnComment;

    public SqlColumnData() {

    }

    public SqlColumnData(String columnName, String dataType, String columnComment) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.columnComment = columnComment;
    }

}
