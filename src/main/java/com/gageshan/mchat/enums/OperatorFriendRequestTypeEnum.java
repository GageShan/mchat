package com.gageshan.mchat.enums;

/**
 * Create by gageshan on 2020/5/3 15:11
 */
public enum  OperatorFriendRequestTypeEnum {
    IGNORE(0,"忽略"),
    PASS(1,"通过");

    public final Integer TYPE;
    public final String MSG;

    OperatorFriendRequestTypeEnum(Integer TYPE, String MSG) {
        this.TYPE = TYPE;
        this.MSG = MSG;
    }
    public Integer getTYPE() {
        return TYPE;
    }

    public static String getMsgByKey(Integer status) {
        for (OperatorFriendRequestTypeEnum
                operatorFriendRequestTypeEnum : OperatorFriendRequestTypeEnum.values()) {
            if(operatorFriendRequestTypeEnum.getTYPE() == status) {
                return operatorFriendRequestTypeEnum.MSG;
            }
        }
        return null;
    }
}
