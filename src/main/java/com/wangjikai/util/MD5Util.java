package com.wangjikai.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by jikai_wang on 2018/1/8.
 * MD5加密：信息摘要算法5，一致散列函数
 */
public class MD5Util {
    public static void main(String[] args) {
        //System.out.println(6>>2);
        //加盐MD5，每个用户的盐值都不一样，保存盐值到数据库用户表
        //盐值不一定在最后或最前，插中间、分开插、倒叙插等方法
//        String password = generateMD5("123456");
//        System.out.println(password);
//        System.out.println(verify("123456", password));
    }

    /**
     * 生成含有随机盐的密码串
     */
    public static String generateMD5(String password) {
        //生成16位随机数盐值，不够的后面补零
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = stringBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                stringBuilder.append("0");
            }
        }
        //盐值
        String salt = stringBuilder.toString();
        //盐与密码一起进行MD摘要得到32位的十六进制字符串
        password = md5Hex(password + salt);
        //将盐值写入48位字符串中
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    public static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验密码是否正确
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        //取出盐值
        String salt = new String(cs2);
        return md5Hex(password + salt).equals(new String(cs1));
    }
}
