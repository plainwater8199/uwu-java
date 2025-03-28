package com.uwu.study.water.db;

import com.uwu.study.water.db.VO.WaterQueryVO;
import com.uwu.study.water.db.VO.WaterVO;
import com.uwu.study.water.db.service.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class DBController {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private WaterService waterService;

    @GetMapping("/tables")
    public void getTables() {
        List<String> tables = databaseService.getTableNames();

    }

    @GetMapping("/tables/{tableName}/columns")
    public ResponseEntity<List<String>> getTableColumns(@PathVariable String tableName) {
        List<String> columns = databaseService.getTableColumns(tableName);
        return ResponseEntity.ok(columns);
    }


    @GetMapping("/tables/checkIndex/{tableName}")
    public ResponseEntity<Boolean> checkIndex(@PathVariable String tableName) {
        Boolean isOK = databaseService.checkIndex(tableName);
        return ResponseEntity.ok(isOK);
    }


    @PostMapping("/tables/water/addDB")
    public ResponseEntity<Boolean> addDB(@RequestBody WaterVO req) {
        return ResponseEntity.ok(waterService.addDB(req));
    }


    @PostMapping("/tables/water/queryByName")
    public ResponseEntity<List<WaterVO>> queryByName(@RequestBody WaterQueryVO query) {
        return ResponseEntity.ok(waterService.queryByName(query));
    }


    @GetMapping("/tables/water/indexTest")
    public ResponseEntity<Boolean> indexTest() {
        return ResponseEntity.ok(waterService.indexTest());
    }
}
