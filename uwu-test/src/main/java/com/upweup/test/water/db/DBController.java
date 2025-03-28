package com.upweup.test.water.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class DBController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/tables")
    public void getTables() {
        List<String> tables = databaseService.getTableNames();

    }

    @GetMapping("/tables/{tableName}/columns")
    public ResponseEntity<List<String>> getTableColumns(@PathVariable String tableName) {
        List<String> columns = databaseService.getTableColumns(tableName);
        return ResponseEntity.ok(columns);
    }
}
