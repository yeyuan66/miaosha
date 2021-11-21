package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.MiaoshaOrderMapper;
import com.imooc.miaosha.dao.OrderInfoMapper;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.enums.OrderStatusEnum;
import com.imooc.miaosha.service.IOrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;


    @Override
    public MiaoshaOrder getBought(Long userId, Long goodsId) {
        return miaoshaOrderMapper.selectByUserGoods(userId,goodsId);
    }

    @Override
    @Transactional
    public OrderInfo createOrderInfo(MiaoshaUser user, GoodsVo goods) {

        //创建订单详情
        OrderInfo orderInfo = new OrderInfo();


        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(OrderStatusEnum.UN_PAY.getCode());
        orderInfo.setUserId(user.getId());



        //创建秒杀订单
        long orderId = orderInfoMapper.insertAndGet(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());

        miaoshaOrderMapper.insertMiaoshaOrder(miaoshaOrder);


        return orderInfo;
    }


}
