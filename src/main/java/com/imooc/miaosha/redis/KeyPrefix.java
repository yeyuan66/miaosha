package com.imooc.miaosha.redis;
/*
* 获取键的前缀接口
* */
public interface KeyPrefix {
    int expireSeconds();//获取过期时间
    String getPrefix();//获取前缀

}
