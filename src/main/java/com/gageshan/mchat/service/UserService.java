package com.gageshan.mchat.service;

import com.gageshan.mchat.enums.MsgSignFlagEnum;
import com.gageshan.mchat.enums.SearchFriendsStatusEnum;
import com.gageshan.mchat.mapper.*;
import com.gageshan.mchat.model.FriendsRequest;
import com.gageshan.mchat.model.MyFriends;
import com.gageshan.mchat.model.User;
import com.gageshan.mchat.model.vo.FriendRequestVO;
import com.gageshan.mchat.model.vo.MyFriendsVO;
import com.gageshan.mchat.netty.ChatMsg;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Create by gageshan on 2020/5/1 23:53
 */
@Service
public class UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersMapperCustom usersMapperCustom;
    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

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

    public User queryUserById(String userId) {
        return usersMapper.selectUserByUserId(userId);
    }

    public User updateUserInfo(User user) {
        usersMapper.updateUserInfo(user);
        return queryUserById(user.getId());
    }

    public Integer preconditionSearchFriends(String myUserId,String friendUsername) {
        User user = queryUserByUsername(friendUsername);
        if(user == null) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.STATUS;
        }
        if(myUserId.equals(user.getId())) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.STATUS;
        }

        MyFriends myFriends = myFriendsMapper.selectFriendsByUserIdAndFriendId(myUserId,user.getId());
        if(myFriends != null) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.STATUS;
        }
        return SearchFriendsStatusEnum.SUCCESS.STATUS;
    }
    public User queryUserByUsername(String username) {
        return usersMapper.selectUserByUserName(username);
    }

    public void sendFriendRequest(String myUserId, String friendUsername) {
        User friend = queryUserByUsername(friendUsername);
        FriendsRequest friendsRequest = friendsRequestMapper.selectRequestByUserIdAndFriendId(myUserId,friend.getId());
        if(friendsRequest == null) {
            FriendsRequest request = new FriendsRequest();
            request.setId(sid.nextShort());
            request.setSendUserId(myUserId);
            request.setAcceptUserId(friend.getId());
            request.setRequestDateTime(new Date());

            friendsRequestMapper.insert(request);
        }
    }

    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return usersMapperCustom.selectFriendRequestList(acceptUserId);
    }

    public void deleteFriendRequest(String sendUserId,String acceptUserId) {
        friendsRequestMapper.deleteFriendRequest(sendUserId,acceptUserId);
    }

    public void passFriendRequest(String sendUserId,String acceptUserId) {
        saveFriend(sendUserId,acceptUserId);
        saveFriend(acceptUserId,sendUserId);
        deleteFriendRequest(sendUserId,acceptUserId);
    }

    public void saveFriend(String sendUserId,String acceptUserId) {
        MyFriends myFriends = new MyFriends();
        myFriends.setId(sid.nextShort());
        myFriends.setMyUserId(sendUserId);
        myFriends.setMyFriendUserId(acceptUserId);
        myFriendsMapper.insert(myFriends);
    }

    public List<MyFriendsVO> queryMyFriends(String userId) {
        return usersMapperCustom.queryMyFriends(userId);
    }


    public String saveMsg(ChatMsg chatMsg) {
        com.gageshan.mchat.model.ChatMsg msgDB = new com.gageshan.mchat.model.ChatMsg();
        msgDB.setId(sid.nextShort());
        msgDB.setAcceptUserId(chatMsg.getReceiverId());
        msgDB.setSendUserId(chatMsg.getSenderId());
        msgDB.setMsg(chatMsg.getMsg());
        msgDB.setCreateTime(new Date());
        msgDB.setSignFlag(MsgSignFlagEnum.UNSIGN.TYPE);
        chatMsgMapper.insert(msgDB);
        return msgDB.getId();
    }

    public void updateMsgSigned(List<String> msgIdList) {
        usersMapperCustom.batchUpdateMsgSignFlag(msgIdList);
    }
}
