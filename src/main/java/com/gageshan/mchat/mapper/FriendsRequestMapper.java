package com.gageshan.mchat.mapper;


import com.gageshan.mchat.model.FriendsRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendsRequestMapper  {
    FriendsRequest selectRequestByUserIdAndFriendId(String myUserId, String friendId);

    void insert(FriendsRequest request);

    void deleteFriendRequest(String sendUserId, String acceptUserId);
}