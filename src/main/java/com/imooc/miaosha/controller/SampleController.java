package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.UserKey;
import com.imooc.miaosha.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/redis/get")
    public Result<User> get(){
        User user = redisService.get(UserKey.getById, "" + 1, User.class);
        return Result.success(user);
    }


    @RequestMapping("/redis/set")
    public Result<Boolean> set(){
        User user = new User();
        user.setId(1);
        user.setName("YaoMing");
        redisService.set(UserKey.getById,""+1,user);
        return Result.success(true);
    }
}
