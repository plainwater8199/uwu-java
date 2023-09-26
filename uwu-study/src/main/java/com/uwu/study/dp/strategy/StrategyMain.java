package com.uwu.study.dp.strategy;

import java.util.Arrays;

public class StrategyMain {


    public static void main(String[] args) {
        int[] a = {9,3,6,1,4,2,8};
        Sorter sorter = new Sorter();
        sorter.sort(a);
        System.out.println(Arrays.toString(a));
    }
}
