package com.uwu.study.water.db.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uwu.study.water.db.VO.WaterQueryVO;
import com.uwu.study.water.db.VO.WaterVO;
import com.uwu.study.water.db.dao.MsgRecordDDLDao;
import com.uwu.study.water.db.dao.WaterTestMapper;
import com.uwu.study.water.db.entity.WaterTest;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class WaterService {

    @Resource
    private WaterTestMapper waterTestMapper;

    @Resource
    private MsgRecordDDLDao msgRecordDDLDao;

    public Boolean addDB(WaterVO req) {
        WaterTest waterTest = new WaterTest();
        waterTest.setName(req.getName());
        waterTest.setType(req.getType());
        waterTest.setDescription(req.getDescription());
        waterTest.setCreator("water");
        waterTest.setCreateTime(LocalDateTime.now());
        waterTest.setMsgId(UUID.randomUUID().toString());
        waterTestMapper.insert(waterTest);

        return true;

    }

    public List<WaterVO> queryByName(WaterQueryVO query) {
        LambdaQueryWrapper<WaterTest> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WaterTest::getName, query.getName());
        List<WaterTest> waterTests = waterTestMapper.selectList(wrapper);
        List<WaterVO> result = new ArrayList<>();
        for (WaterTest waterTest : waterTests) {
            WaterVO waterVO = new WaterVO();
            waterVO.setName(waterTest.getName());
            waterVO.setType(waterTest.getType());
            waterVO.setMsgId(waterTest.getMsgId());
            waterVO.setDescription(waterTest.getDescription());
            result.add(waterVO);
        }
        return result;
    }

    public Boolean indexTest() {

        String tableName = "msg_record_4015595232";

        List<String> indexList = Arrays.asList("idx_account_id", "idx_creator", "idx_message_id", "idx_phone_num", "idx_account_type", "idx_plan_detail_id", "idx_send_result");
        List<String> indexListCopy = new ArrayList<>(indexList);


        List<Map<String, Object>> indexList2 = msgRecordDDLDao.getTableIndexes(tableName);
        for (Map<String, Object> index : indexList2) {
            String indexName = (String) index.get("Key_name");
//            if (!"PRIMARY".equals(indexName)) {
//                if(!indexList.contains(indexName)){
//                    msgRecordDDLDao.dropIndex(tableName,indexName);
//                }else{
//                    indexListCopy.remove(indexName);
//                }
//            }
        }

//        for (String indexName : indexListCopy) {
//            String index = indexName.replace("idx_", "");
//            msgRecordDDLDao.addIndex(tableName,indexName,index);
//        }



//        try {
//            msgRecordDDLDao.addIndexForAccountId(tableName);
//            msgRecordDDLDao.addIndexForCreator(tableName);
//            msgRecordDDLDao.addIndexForMessageId(tableName);
//            msgRecordDDLDao.addIndexForPhoneNum(tableName);
//            msgRecordDDLDao.addIndexForAccountType(tableName);
//            msgRecordDDLDao.addIndexForPlanDetailId(tableName);
//            msgRecordDDLDao.addIndexForSendResult(tableName);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


//        List<Map<String, Object>> indexList = msgRecordDDLDao.getTableIndexes(tableName);

//        for (Map<String, Object> index : indexList) {
//            String indexName = (String) index.get("Key_name");
//            System.out.println("Index Name: " + indexName);
//            if (!"PRIMARY".equals(indexName)) {
//                msgRecordDDLDao.dropIndex(tableName,indexName);
//            }
//        }

//        List<Map<String, Object>> indexList2 = msgRecordDDLDao.getTableIndexes(tableName);
//
//        for (Map<String, Object> index : indexList2) {
//            String indexName = (String) index.get("Key_name");
//            System.out.println("Index Name: " + indexName);
//        }

        return true;

    }
}
