//package com.uwu.study.db;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.uwu.study.db.info.dao.DictTypeDao;
//import com.uwu.study.db.info.dao.DictValueDao;
//import com.uwu.study.db.info.dao.UserDao;
//import com.uwu.study.db.info.entity.DictTypeDo;
//import com.uwu.study.db.info.entity.DictValueDo;
//import com.uwu.study.db.info.entity.UserDo;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ShardingJDBCTest {
//
//    @Resource
//    private UserDao userDao;
//
//    @Resource
//    private DictTypeDao dictTypeDao;
//
//    @Resource
//    private DictValueDao dictValueDao;
//
//
//
//    @Test
//    public void count(){
//        System.out.println(userDao.count());
//    }
//
//
//    @Test
//    public void addUser(){
//        for(int i = 0;i<10;i++){
//            UserDo user = new UserDo();
////            user.setUserId("uwu"+String.valueOf(new Date().getTime())+i);
//            user.setName("uwu"+i);
//            user.setPhone(Long.valueOf("1810808080"+i));
//            user.setMail("18118"+i+"@qq.com");
//            user.setCreator("system");
//            user.setCreateTime(new Date());
//            user.setUpdater("system");
//            user.setUpdateTime(new Date());
//            userDao.insert(user);
//        }
//    }
//
//    @Test
//    public void queryUser(){
//        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserDo::getUserId,913732921347543041L);
//        List<UserDo> users = userDao.selectList(queryWrapper);
//        users.forEach(i-> System.out.println(i));
//    }
//
//    @Test
//    public void queryUsers(){
//        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.between(UserDo::getUserId,913751090317496320L,913751091294769153L);
////        queryWrapper.in(User::getUserId, 913751091093442561L,913751091504484353L,913751091672256512L);
//        List<UserDo> users = userDao.selectList(queryWrapper);
//        users.forEach(i-> System.out.println(i));
//    }
//
//
//    @Test
//    public void queryUserByPhone(){
//        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(UserDo::getPhone,"18108080805");//也可以查询，是所有表都查询
//        List<UserDo> users = userDao.selectList(queryWrapper);
//        users.forEach(i-> System.out.println(i));
//    }
//    @Test
//    public void queryUserSort(){
//        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByDesc(UserDo::getPhone);//也可以排序，是所有表都查询
//        List<UserDo> users = userDao.selectList(queryWrapper);
//        users.forEach(i-> System.out.println(i));
//    }
//
//
//    @Test
//    public void queryUserComplex(){
//        LambdaQueryWrapper<UserDo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.between(UserDo::getUserId,913751090317496320L,913751091294769153L);//也可以排序，是所有表都查询
//        queryWrapper.eq(UserDo::getPhone,18108080801L);
//        List<UserDo> users = userDao.selectList(queryWrapper);
//        users.forEach(i-> System.out.println(i));
//    }
//
////    @Test
////    public void queryCourseHint(){
////        HintManager hintManager = HintManager.getInstance();
////        hintManager.addTableShardingValue("user",2);
////
////        List<User> users = userDao.selectList(null);
////        users.forEach(i-> System.out.println(i));
////
////        hintManager.close();//保证线程安全，用完后关闭
////
////    }
//
//    @Test
//    public void addDictType(){
//        DictTypeDo dictType = new DictTypeDo();
//        dictType.setTypeCode("language");
//        dictType.setTypeStatus(0);
//        dictType.setCreator("system");
//        dictType.setCreateTime(new Date());
//
//        dictTypeDao.insert(dictType);
//
//        DictValueDo dictValue = new DictValueDo();
//        dictValue.setTypeCode("language");
//        dictValue.setValueCode("English");
//        dictValue.setValue("英语");
//        dictValue.setValueStatus(0);
//        dictValue.setSort(1);
//        dictValue.setCreator("system");
//        dictValue.setCreateTime(new Date());
//        dictValueDao.insert(dictValue);
//
//        DictValueDo dictValue2 = new DictValueDo();
//        dictValue2.setTypeCode("language");
//        dictValue2.setValueCode("Chinese");
//        dictValue2.setValue("中文");
//        dictValue2.setValueStatus(0);
//        dictValue2.setSort(2);
//        dictValue2.setCreator("system");
//        dictValue2.setCreateTime(new Date());
//        dictValueDao.insert(dictValue2);
//
//
//    }
//
//
//    @Test
//    public void addDictTypeForRWSplit(){
//        DictTypeDo dictType = new DictTypeDo();
//        dictType.setTypeCode("Education");
//        dictType.setTypeStatus(0);
//        dictType.setCreator("system");
//        dictType.setCreateTime(new Date());
//
//        dictTypeDao.insert(dictType);
//
//    }
//
//    @Test
//    public void queryDictTypeForRWSplit(){
//
//        for(int i= 0;i<10;i++){
//            List<DictTypeDo> dictTypes = dictTypeDao.selectList(null);
//            dictTypes.forEach(a-> System.out.println(a));
//        }
//    }
//
//}
