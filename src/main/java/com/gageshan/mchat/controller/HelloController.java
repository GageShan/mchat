package com.gageshan.mchat.controller;

import com.gageshan.mchat.utils.IMoocJSONResult;
import com.gageshan.mchat.utils.JsonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by gageshan on 2020/5/1 20:39
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return JsonUtils.objectToJson(IMoocJSONResult.ok("hello world"));
    }
}
