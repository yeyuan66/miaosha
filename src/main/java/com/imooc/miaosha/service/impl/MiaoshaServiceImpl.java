package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.service.IGoodsService;
import com.imooc.miaosha.service.IMiaoshaService;
import com.imooc.miaosha.service.IOrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MiaoshaServiceImpl implements IMiaoshaService {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IOrderService orderService;

    @Override
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {

        //1.扣库存
        goodsService.decr_Stock(goods.getId());



        //2. 生成订单详情,存入秒杀简略订单




        return orderService.createOrderInfo(user, goods);
    }

    //扣库存、生成订单、生成订单操作的原子操作
}
