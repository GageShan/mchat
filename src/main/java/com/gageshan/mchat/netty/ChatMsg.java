package com.gageshan.mchat.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by gageshan on 2020/5/4 11:10
 */
@Data
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 3611169682695799175L;

    private String senderId;
    private String receiverId;
    private String msg;
    private String msgId;
}
