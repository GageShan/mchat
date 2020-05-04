package com.gageshan.mchat.mapper;

import com.gageshan.mchat.model.ChatMsg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMsgMapper {
    void insert(ChatMsg msgDB);
}