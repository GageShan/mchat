package com.gageshan.mchat.netty;

/**
 * Create by gageshan on 2020/5/4 11:35
 */

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 用户id与channel关联处理
 */
public class UserChannelRel {
    private static HashMap<String, Channel> manager = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        manager.put(senderId,channel);
    }

    public static Channel get(String senderId) {
        return manager.get(senderId);
    }
}
