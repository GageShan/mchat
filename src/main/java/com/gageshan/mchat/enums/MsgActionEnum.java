package com.gageshan.mchat.enums;

/**
 * Create by gageshan on 2020/5/4 11:21
 */
public enum MsgActionEnum {
    CONNECT(1,"第一次重连或初始化连接"),
    CHAT(2,"聊天消息"),
    SIGNED(3,"消息签收"),
    KEEPALIVE(4,"客户端保持心跳"),
    PULL_FRIEND(5,"拉取好友");

    public final Integer TYPE;
    public final String CONTENT;

    MsgActionEnum(Integer TYPE, String CONTENT) {
        this.TYPE = TYPE;
        this.CONTENT = CONTENT;
    }
    public Integer getTYPE() {
        return TYPE;
    }

    public static String getMsgByKey(Integer status) {
        for (MsgActionEnum
                msgActionEnum : MsgActionEnum.values()) {
            if(msgActionEnum.getTYPE() == status) {
                return msgActionEnum.CONTENT;
            }
        }
        return null;
    }
}
