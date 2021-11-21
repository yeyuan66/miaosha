package com.imooc.miaosha.enums;

import lombok.Getter;

@Getter
public enum  GoodsStatusEnum {
    ON_MIAO(1,"秒杀进行中"),
    NOT_BEGIN(2,"秒杀未开始"),
    END(3,"秒杀已结束");
    Integer code;
    String msg;

    GoodsStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
