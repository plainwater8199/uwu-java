package com.uwu.study.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uwu.study.db.shardingspheredemo.entity.DictType;
import com.uwu.study.db.shardingspheredemo.entity.DictValue;
import com.uwu.study.db.shardingspheredemo.entity.User;
import com.uwu.study.db.shardingspheredemo.mapper.DictTypeMapper;
import com.uwu.study.db.shardingspheredemo.mapper.DictValueMapper;
import com.uwu.study.db.shardingspheredemo.mapper.UserMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.bouncycastle.asn1.ocsp.CertID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJDBCTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictValueMapper dictValueMapper;



    @Test
    public void count(){
        System.out.println(userMapper.count());
    }


    @Test
    public void addUser(){
        for(int i = 0;i<10;i++){
            User user = new User();
//            user.setUserId("uwu"+String.valueOf(new Date().getTime())+i);
            user.setName("uwu"+i);
            user.setPhone(Long.valueOf("1810808080"+i));
            user.setMail("18118"+i+"@qq.com");
            user.setCreator("system");
            user.setCreateTime(new Date());
            user.setUpdater("system");
            user.setUpdateTime(new Date());
            userMapper.insert(user);
        }
    }

    @Test
    public void queryUser(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId,913732921347543041L);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(i-> System.out.println(i));
    }

    @Test
    public void queryUsers(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(User::getUserId,913751090317496320L,913751091294769153L);
//        queryWrapper.in(User::getUserId, 913751091093442561L,913751091504484353L,913751091672256512L);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(i-> System.out.println(i));
    }


    @Test
    public void queryUserByPhone(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,"18108080805");//也可以查询，是所有表都查询
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(i-> System.out.println(i));
    }
    @Test
    public void queryUserSort(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(User::getPhone);//也可以排序，是所有表都查询
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(i-> System.out.println(i));
    }


    @Test
    public void queryUserComplex(){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(User::getUserId,913751090317496320L,913751091294769153L);//也可以排序，是所有表都查询
        queryWrapper.eq(User::getPhone,18108080801L);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(i-> System.out.println(i));
    }

    @Test
    public void queryCourseHint(){
        HintManager hintManager = HintManager.getInstance();
        hintManager.addTableShardingValue("user",2);

        List<User> users = userMapper.selectList(null);
        users.forEach(i-> System.out.println(i));

        hintManager.close();//保证线程安全，用完后关闭

    }

    @Test
    public void addDictType(){
        DictType dictType = new DictType();
        dictType.setTypeCode("language");
        dictType.setTypeStatus(0);
        dictType.setCreator("system");
        dictType.setCreateTime(new Date());

        dictTypeMapper.insert(dictType);

        DictValue dictValue = new DictValue();
        dictValue.setTypeCode("language");
        dictValue.setValueCode("English");
        dictValue.setValue("英语");
        dictValue.setValueStatus(0);
        dictValue.setSort(1);
        dictValue.setCreator("system");
        dictValue.setCreateTime(new Date());
        dictValueMapper.insert(dictValue);

        DictValue dictValue2 = new DictValue();
        dictValue2.setTypeCode("language");
        dictValue2.setValueCode("Chinese");
        dictValue2.setValue("中文");
        dictValue2.setValueStatus(0);
        dictValue2.setSort(2);
        dictValue2.setCreator("system");
        dictValue2.setCreateTime(new Date());
        dictValueMapper.insert(dictValue2);


    }


    @Test
    public void addDictTypeForRWSplit(){
        DictType dictType = new DictType();
        dictType.setTypeCode("Education");
        dictType.setTypeStatus(0);
        dictType.setCreator("system");
        dictType.setCreateTime(new Date());

        dictTypeMapper.insert(dictType);

    }

    @Test
    public void queryDictTypeForRWSplit(){

        for(int i= 0;i<10;i++){
            List<DictType> dictTypes = dictTypeMapper.selectList(null);
            dictTypes.forEach(a-> System.out.println(a));
        }
    }

}
