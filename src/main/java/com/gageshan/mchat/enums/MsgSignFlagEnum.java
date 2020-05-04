package com.gageshan.mchat.enums;

/**
 * Create by gageshan on 2020/5/4 13:34
 */
public enum MsgSignFlagEnum {
    UNSIGN(0,"未签收"),
    SIGNED(1,"已签收");

    public final Integer TYPE;
    public final String CONTENT;

    MsgSignFlagEnum(Integer TYPE, String CONTENT) {
        this.TYPE = TYPE;
        this.CONTENT = CONTENT;
    }

    public Integer getTYPE() {
        return TYPE;
    }

    public static String getMsgByKey(Integer status) {
        for (MsgSignFlagEnum msgSignFlagEnum : MsgSignFlagEnum.values()) {
            if(msgSignFlagEnum.getTYPE() == status) {
                return msgSignFlagEnum.CONTENT;
            }
        }
        return null;
    }

}
