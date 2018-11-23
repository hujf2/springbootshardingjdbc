package com.hongka.springboot.shardingjdbc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongka.springboot.shardingjdbc.entity.User;
import com.hongka.springboot.shardingjdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.hongka.springboot.shardingjdbc.common.BaseController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author quxinyong
 * @since 2018-11-21
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public JSONObject add(User item) {
        boolean success = userService.save(item);
        return JSONObject.parseObject("{\"result\":\"" + success + "\"}");
    }

    @RequestMapping("/list")
    public Object list() {
        List<User> items = userService.list();
        return JSONArray.toJSON(items);
    }
}
