package top.xingzhaohu1212.blog.lib;

import  java.util.Random;
import java.util.UUID;

//随机生成一串字符串
public class RandomGenerateStr {

    public static String getRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random  = new Random();
        StringBuilder str1 = new StringBuilder();
        for (int i = 0; i < length; i ++) {
            int number = random.nextInt(55);
            str1.append(str.charAt(number));
        }
        System.out.println(str1.toString());
        return str1.toString();
    }

    public static String getRandomStr_1() {
        String uuid = UUID.randomUUID().toString();

        uuid = uuid.replace("-", "");

        return uuid;
    }
}
