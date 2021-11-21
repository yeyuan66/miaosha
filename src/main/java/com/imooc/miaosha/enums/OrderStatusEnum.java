package com.imooc.miaosha.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
//    Received, refunded, completed
    UN_PAY(0,"未支付"),
    FOR_SHIPPING(1,"待发货"),
    SHIPPED(2,"已发货"),
    RECEIVED(3,"已收货"),
    REFUNDED(4,"已退款"),
    COMPLETED(5,"已退款");
    Integer code;
    String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
