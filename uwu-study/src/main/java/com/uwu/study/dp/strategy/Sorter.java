package com.uwu.study.dp.strategy;

public class Sorter {
    //选择排序
    public void sort(int[] arr) {
        for (int i =0; i<arr.length-1;i++){
            int minPos = i;
            for(int j = i+1; j<arr.length;j++){
                minPos = arr[j]<arr[minPos] ? j : minPos;
            }
            swap(arr,i,minPos);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
