package com.lh.daily.handler;
//String异常类
public class StringHandler {
    public static boolean stringtoLongException(String str) { //出现字符串转换异常，说明输入的是新增数据
        try {
            new Long(str);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
}
