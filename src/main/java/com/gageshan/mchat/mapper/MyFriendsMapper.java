package com.gageshan.mchat.mapper;


import com.gageshan.mchat.model.MyFriends;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyFriendsMapper  {
    MyFriends selectFriendsByUserIdAndFriendId(String myUserId, String friendId);

    void insert(MyFriends myFriends);
}