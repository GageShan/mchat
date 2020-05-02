package com.gageshan.mchat.mapper;


import com.gageshan.mchat.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper{
    User selectUserByUserName(String username);

    User selectUserByUsernameAndPassword(String username, String password);

    void insert(User user);
}