package com.uwu.study.db.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WorkTest {

    private static String getRandomCspId() {
        List<String> cspIdList = new ArrayList<>(Arrays.asList("0699286243","0974502530","5513272552","6327883466","6862629809","7804731298"));
        return cspIdList.get(ThreadLocalRandom.current().nextInt(0, cspIdList.size() - 1));
    }
    public static void main(String[] args) {
        for(int i = 1; i < 20; i++){
            System.out.println(getRandomCspId());
        }


    }
}
