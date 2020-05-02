package com.gageshan.mchat.controller;

import com.gageshan.mchat.model.User;
import com.gageshan.mchat.model.vo.UserVO;
import com.gageshan.mchat.service.UserService;
import com.gageshan.mchat.utils.IMoocJSONResult;
import com.gageshan.mchat.utils.JsonUtils;
import com.gageshan.mchat.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            user.setFaceImage("");
            user.setFaceImageBig("");
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            dbUser = userService.saveUser(user);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(dbUser,userVO);
        return JsonUtils.objectToJson(IMoocJSONResult.ok(userVO));
    }
}
