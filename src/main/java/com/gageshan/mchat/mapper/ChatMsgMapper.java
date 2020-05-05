package com.gageshan.mchat.mapper;

import com.gageshan.mchat.model.ChatMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMsgMapper {
    void insert(ChatMsg msgDB);

    List<ChatMsg> selectMsgByUserId(int flag, String acceptUserId);
}