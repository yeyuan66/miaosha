package com.imooc.miaosha.util;

import java.util.UUID;

public class UUidUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
