package com.gageshan.mchat.model;

import lombok.Data;

/**
 * Create by gageshan on 2020/5/1 21:59
 */
@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String nickname;
    private String faceImage;
    private String faceImageBig;
    private String qrcode;
    private String cid;
}
