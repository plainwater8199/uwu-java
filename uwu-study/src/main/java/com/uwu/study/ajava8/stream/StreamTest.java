package com.uwu.study.ajava8.stream;



import cn.hutool.json.JSONUtil;
import com.uwu.study.ajava8.stream.dto.DataItem;
import com.uwu.study.ajava8.stream.dto.TodoItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) {
        List<DataItem> testData = DataFactory.getTestData(10);
        List<DataItem> testDataNew = new ArrayList<>(testData.stream().collect(Collectors.toMap(DataItem::getName, a -> a, (o1, o2) -> {
            o1.getTodoList().addAll(o2.getTodoList());o1.setAge(o1.getAge()+o2.getAge());
            return o1;
        })).values());
        testDataNew.forEach(i-> i.setTodoList(i.getTodoList().stream().sorted(Comparator.comparing(TodoItem::getTime)).collect(Collectors.toList())));


        String dataJson = String.valueOf(JSONUtil.parse(testDataNew));
        System.out.println(DataFactory.format(dataJson));
//        testData = testData.stream().filter(DataItem -> !CollectionUtils.isEmpty(DataItem.getTodoList())).collect(Collectors.toList());
//        testData.forEach(i -> System.out.println(i));
    }




}
