package com.aitao.myblog.utils;

import com.aitao.myblog.domain.BlogLabel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Author : AiTao
 * Date : 2020/11/12
 * Time : 16:22
 * Information :
 */
@Component
public class StringUtils {
    /**
     * 字符串转整数
     *
     * @param str 字符串
     * @return 整数
     */
    public static Long strToLong(String str) {
        char[] chars = str.trim().toCharArray();
        if (chars.length == 0) {
            return 0L;
        }
        long res = 0;//结果
        int index = 1;//+或-号
        int flag = 1;//正、负
        if (chars[0] == '-') {//判断第一个符号是负号还是正号
            flag = -1;
        } else if (chars[0] != '+') {
            index = 0;
        }
        //如果首位没有符号，则从0开始遍历，否则从1开始遍历
        for (int i = index; i < chars.length; i++) {
            //判断字符是否为一个数字
            if (!Character.isDigit(chars[i])) {
                break;
            }
            //将字符串转换成数字
            res = res * 10 + (chars[i] - '0');
        }
        return (res * flag);
    }

    /**
     * 获取唯一标识UUID
     *
     * @return 返回一个UUID标签
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 将一个集合转换成 "1,2,3,4" 形式的字符串
     *
     * @return 返回转换后的字符串数据
     */
    public static String listToString(List<Long> labels) {
        StringBuilder res = new StringBuilder();
        int len = labels.size();
        for (int i = 0; i < len; i++) {
            res.append(labels.get(i));
            if((i+1) != len){
                res.append(",");
            }
        }
        return res.toString();
    }
}
