package com.gageshan.mchat.netty;

import lombok.Data;
import java.io.Serializable;

/**
 * Create by gageshan on 2020/5/4 11:07
 */
@Data
public class DataContent implements Serializable {
    private static final long serialVersionUID = 8021381444738260454L;
    private Integer action;
    private ChatMsg chatMsg;
    private String extend;
}
