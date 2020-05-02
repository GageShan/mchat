package com.gageshan.mchat.model;

import lombok.Data;

import java.util.Date;

/**
 * Create by gageshan on 2020/5/1 22:06
 */
@Data
public class FriendsRequest {
    private String id;
    private String sendUserId;
    private String acceptUserId;
    private Date requestDateTime;
}
