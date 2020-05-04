package com.gageshan.mchat.utils;

import java.util.Random;

/**
 * Create by gageshan on 2020/5/3 18:21
 */
public class UserUtils {
    public static String getHeaderUrl() {
        return String.format("http://images.nowcoder.com/head/%dm.png",new Random().nextInt(1000));
    }
}
