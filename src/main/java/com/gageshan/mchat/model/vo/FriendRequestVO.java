package com.gageshan.mchat.model.vo;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/3 14:10
 */
@Data
public class FriendRequestVO {
    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;
}
