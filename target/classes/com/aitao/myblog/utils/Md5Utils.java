package com.aitao.myblog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public final class Md5Utils {
    private static Logger logger = LoggerFactory.getLogger(Md5Utils.class);
    /**
     * MD5加密
     * @param psd 加载字符串
     * @return 返回加密后的字符串
     */
    public static String encrypt(String psd) {
        //密码不能为空
        if (psd == null || psd.equals("")) {
            logger.warn("密码不能为空!!!");
            return null;
        }

        try {
            char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] bytes = psd.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            logger.warn("MD5加密出错！！" + e);
        }
        return "";
    }
}