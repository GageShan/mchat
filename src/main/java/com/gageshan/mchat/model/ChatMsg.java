package com.gageshan.mchat.model;

import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/1 22:04
 */
@Data
public class ChatMsg {
    private String id;
    private String sendUserId;
    private String acceptUserId;
    private String msg;
    private Integer signFlag;
    private Date createTime;
}
