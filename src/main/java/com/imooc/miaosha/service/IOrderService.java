package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

public interface IOrderService {
    MiaoshaOrder getBought(Long userId,Long goodsId);

    OrderInfo createOrderInfo(MiaoshaUser user, GoodsVo goods);
}
