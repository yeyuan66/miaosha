package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.service.IGoodsService;
import com.imooc.miaosha.service.IMiaoshaService;
import com.imooc.miaosha.service.IOrderService;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/*
*
* 重点内容
* */
@Controller
@RequestMapping("/miaosha")
@Slf4j
public class MiaoshaController {

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IOrderService orderService;

    @Autowired
    IMiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String doMiaosha(Model model, MiaoshaUser user, @RequestParam long goodsId){

        //判断一下登录状态
        model.addAttribute("user",user);
        if(user == null)return "login";


        //查商品余量，没货不能秒杀
        GoodsVo goodsVo = goodsService.getVoById(goodsId);
        log.info("goodsId:{}",goodsId);
        if(goodsVo.getStockCount()<=0){
            model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }
        //查看秒杀订单详情，已经秒杀过的用户不能再次秒杀
        //设置了唯一索引，就不会出现超卖
        //可以把订单信息存进redis中
        else if(orderService.getBought(user.getId(),goodsId) != null){
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //可以秒杀，扣库存，写入订单和订单详情

//        goodsService.decr_Stock(goodsId); 不要在这扣库存，不能实现事务


        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        //返回订单信息和商品信息给前端
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);



        return "order_detail";
    }
}
