package com.gageshan.mchat.model.vo;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/1 21:59
 */
@Data
public class UserVO {
    private String id;
    private String username;
    private String nickname;
    private String faceImage;
    private String faceImageBig;
    private String qrcode;
}
