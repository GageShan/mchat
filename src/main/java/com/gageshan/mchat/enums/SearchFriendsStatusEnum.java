package com.gageshan.mchat.enums;

/**
 * Create by gageshan on 2020/5/3 11:32
 */
public enum  SearchFriendsStatusEnum {
    SUCCESS(1,"ok"),
    USER_NOT_EXIST(1,"无此用户"),
    NOT_YOURSELF(2,"不能添加自己"),
    ALREADY_FRIENDS(3,"该用户已经是你的好友");

    public final Integer STATUS;
    public final String MSG;

    SearchFriendsStatusEnum(Integer STATUS, String MSG) {
        this.STATUS = STATUS;
        this.MSG = MSG;
    }

    public Integer getSTATUS() {
        return STATUS;
    }

    public static String getMsgByKey(Integer status) {
        for (SearchFriendsStatusEnum friendsStatusEnum : SearchFriendsStatusEnum.values()) {
            if(friendsStatusEnum.getSTATUS() == status) {
                return friendsStatusEnum.MSG;
            }
        }
        return null;
    }
}
