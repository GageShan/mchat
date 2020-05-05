package com.gageshan.mchat.controller;

import com.gageshan.mchat.enums.OperatorFriendRequestTypeEnum;
import com.gageshan.mchat.enums.SearchFriendsStatusEnum;
import com.gageshan.mchat.model.ChatMsg;
import com.gageshan.mchat.model.User;
import com.gageshan.mchat.model.bo.UserBo;
import com.gageshan.mchat.model.vo.MyFriendsVO;
import com.gageshan.mchat.model.vo.UserVO;
import com.gageshan.mchat.service.UserService;
import com.gageshan.mchat.utils.IMoocJSONResult;
import com.gageshan.mchat.utils.JsonUtils;
import com.gageshan.mchat.utils.MD5Utils;
import com.gageshan.mchat.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Create by gageshan on 2020/5/1 23:24
 */
@Controller
@RequestMapping("u")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registOrLogin")
    @ResponseBody
    public String registOrLogin(@RequestBody User user) throws Exception {
        System.out.println(user.toString());
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JsonUtils.objectToJson(IMoocJSONResult.errorMsg("用户名或密码不能为空"));
        }
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        User dbUser = null;
        if(usernameIsExist) {
            //登录
            dbUser = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
            if(dbUser == null) {
                return JsonUtils.objectToJson(IMoocJSONResult.errorMsg("用户名或密码不正确"));
            }
        } else {
            //注册
            user.setNickname(user.getUsername());
            user.setFaceImage(UserUtils.getHeaderUrl());
            user.setFaceImageBig(UserUtils.getHeaderUrl());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            dbUser = userService.saveUser(user);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(dbUser,userVO);
        return JsonUtils.objectToJson(IMoocJSONResult.ok(userVO));
    }

    @PostMapping("/setNickname")
    @ResponseBody
    public IMoocJSONResult setNickname(@RequestBody UserBo userBo) {
        User user = new User();
        user.setId(userBo.getUserId());
        user.setNickname(userBo.getNickname());
        user = userService.updateUserInfo(user);
        System.out.println(user);
        return IMoocJSONResult.ok(user);
    }

    @PostMapping("/search")
    @ResponseBody
    public IMoocJSONResult searchUser(String myUserId, String friendUsername) {
        if(StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return IMoocJSONResult.errorMsg("");
        }
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if(status == SearchFriendsStatusEnum.SUCCESS.STATUS) {
            User user = userService.queryUserByUsername(friendUsername);
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            return IMoocJSONResult.ok(userVO);
        } else {
            return IMoocJSONResult.errorMsg(SearchFriendsStatusEnum.getMsgByKey(status));
        }
    }

    @PostMapping("/addFriendRequest")
    @ResponseBody
    public IMoocJSONResult addFriendRequest(String myUserId, String friendUsername) {
        if(StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return IMoocJSONResult.errorMsg("");
        }
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if(status == SearchFriendsStatusEnum.SUCCESS.STATUS) {
            userService.sendFriendRequest(myUserId,friendUsername);
            return IMoocJSONResult.ok();
        } else {
            return IMoocJSONResult.errorMsg(SearchFriendsStatusEnum.getMsgByKey(status));
        }
    }
    @PostMapping("/queryFriendRequests")
    @ResponseBody
    public IMoocJSONResult queryFriendRequests(String userId) {
        if(StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("");
        }
        return IMoocJSONResult.ok(userService.queryFriendRequestList(userId));
    }

    @PostMapping("/operFriendRequest")
    @ResponseBody
    public IMoocJSONResult operFriendRequest(String acceptUserId,String sendUserId,Integer operType) {
        if(StringUtils.isBlank(acceptUserId) || StringUtils.isBlank(sendUserId) || operType == null) {
            return IMoocJSONResult.errorMsg("");
        }

        if(StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByKey(operType))) {
            return IMoocJSONResult.errorMsg("");
        }
        if(operType == OperatorFriendRequestTypeEnum.IGNORE.TYPE) {
            userService.deleteFriendRequest(sendUserId, acceptUserId);
        } else if(operType == OperatorFriendRequestTypeEnum.PASS.TYPE) {
            userService.passFriendRequest(sendUserId, acceptUserId);
        }
        List<MyFriendsVO> myFriends = userService.queryMyFriends(acceptUserId);
        return IMoocJSONResult.ok(myFriends);
    }

    @PostMapping("/myFriends")
    @ResponseBody
    public IMoocJSONResult myFriends(String userId) {
        if(StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("");
        }
        List<MyFriendsVO> myFriends = userService.queryMyFriends(userId);
//        System.out.println(myFriends.toString());
        return IMoocJSONResult.ok(myFriends);
    }

    @PostMapping("/getUnReadMsgList")
    @ResponseBody
    public IMoocJSONResult getUnReadMsgList(String acceptUserId) {
        if(StringUtils.isBlank(acceptUserId)) {
            return IMoocJSONResult.errorMsg("");
        }
        List<ChatMsg> unReadMsgList = userService.getUnReadMsgList(acceptUserId);
//        System.out.println(myFriends.toString());
        return IMoocJSONResult.ok(unReadMsgList);
    }
}
