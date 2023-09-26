package com.uwu.study.ajava8.stream;



import cn.hutool.json.JSONUtil;
import com.uwu.study.ajava8.stream.dto.DataItem;
import com.uwu.study.ajava8.stream.dto.TestData;
import com.uwu.study.ajava8.stream.dto.TodoItem;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DataFactory {

    private DataFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static List<DataItem> getTestData(int sum) {
        List<DataItem> dataList = new ArrayList<>();
        DataItem testData ;
        for(int id = 0; id <=sum ; id ++ ){
            testData = new DataItem();
            testData.setId(id);
            testData.setName(obtainName());
            testData.setAge(obtainAge());
            testData.setTodoList(todoLists());
            testData.setSuccess(isSuccess());
            dataList.add(testData);
        }
        TestData data = new TestData();
        data.setDataItems(dataList);
        String dataJson = String.valueOf(JSONUtil.parse(data));
        System.out.println(format(dataJson));
        System.out.println("-----------------------------------生成数据--------------------------------------------");
        return dataList;
    }

    private static List<TodoItem> todoLists() {
        List<TodoItem> todoItems = new ArrayList<>();
        List<String> todos = Arrays.asList("看电影","睡觉","打篮球","玩游戏","游泳","看电影","睡觉","打篮球","玩游戏","游泳");
        TodoItem todoItem ;
        for(int i = 0 ; i < 5; i++ ){
            todoItem = new TodoItem();
            todoItem.setItem(todos.get(randomInt()));
            todoItem.setTime(obtainTime());
            todoItems.add(todoItem);
        }
        return todoItems.stream().sorted(Comparator.comparing(TodoItem::getTime)).collect(Collectors.toList());
    }

    private static String obtainTime() {
        int x = ((int)(Math.random() * 100000)) % 1000;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        calendar.add(Calendar.MINUTE,-x);
        return format.format(calendar.getTime());
    }

    private static int randomInt() {
        Random random = new Random();
        return random.nextInt(10);
    }

    private static String obtainName() {
        int data = randomInt();
        if(data < 4){
            return "路飞";
        }else if(data < 7){
            return "乔巴";
        }else{
            return "娜美";
        }
    }

    private static Boolean isSuccess() {
        return randomInt() > 7;
    }

    private static List<String> randomLikes() {
        int data = randomInt();
        if (data < 2){
            return Collections.emptyList();
        } else if (data < 4){
            return Arrays.asList("打篮球","游泳");
        } else if (data <6){
            return Arrays.asList("打篮球","玩游戏");
        } else if (data <8) {
            return Arrays.asList("看电影","睡觉");
        } else{
            return Arrays.asList("看电影","睡觉","打篮球","玩游戏");
        }

    }

    private static Integer obtainAge() {
        return (int)(Math.random()*40)+10;
    }



    /**
     * 得到格式化json数据  退格用\t 换行用\r
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c+"\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
