package com.gageshan.mchat.service;

import com.gageshan.mchat.mapper.UsersMapper;
import com.gageshan.mchat.model.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by gageshan on 2020/5/1 23:53
 */
@Service
public class UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        User user = usersMapper.selectUserByUserName(username);
        return user == null ? false : true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryUserForLogin(String username,String password) {
        User user = usersMapper.selectUserByUsernameAndPassword(username,password);
        return user;
    }

    public User saveUser(User user) {
        user.setId(sid.nextShort());
        user.setQrcode("");
        usersMapper.insert(user);
        return user;
    }
}
