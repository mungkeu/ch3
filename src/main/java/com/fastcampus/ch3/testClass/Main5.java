package com.fastcampus.ch3.testClass;

public class Main5 {
    public static void main(String[] args) {
        int[] num = new int[]{1,2,3,4,5};

        for(int i : num)
        {
            System.out.println(num[i-1]);
        }
    }
}
