package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.GoodsKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.IGoodsService;
import com.imooc.miaosha.service.IMiaoshaUserService;
import com.imooc.miaosha.service.impl.MiaoshaUserServiceImpl;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.List;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IMiaoshaUserService userService;

    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;
    @RequestMapping(value = "/to_list", produces = "text/html")
    public String list(HttpServletRequest request, HttpServletResponse response, Model model,
                       MiaoshaUser user){

        //从cookie中拿或者从参数中拿

        model.addAttribute("user",user);
        List<GoodsVo> goodsList = goodsService.list();
        model.addAttribute("goodsList",goodsList);

        //return "goods_list";
/*
        SpringWebContext ctx = new SpringWebContext(request,response,
                request.getServletContext(),request.getLocale(), model.asMap(), applicationContext );*/

        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());

        //手动渲染
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!org.apache.commons.lang3.StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @RequestMapping(value="/to_detail2/{goodsId}",produces="text/html")
    @ResponseBody
    public String detail2(HttpServletRequest request, HttpServletResponse response, Model model,MiaoshaUser user,
                          @PathVariable("goodsId")long goodsId) {
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!org.apache.commons.lang3.StringUtils.isEmpty(html)) {
            return html;
        }
        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //跑来controller层做时间判断？？
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
//        return "goods_detail";

        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());

        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!org.apache.commons.lang3.StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;
    }


    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId){

        //从cookie中拿或者从参数中拿

        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.getVoById(goodsId);

        model.addAttribute("goods",goodsVo);
//        model.addAttribute("miaoshaStatus",goodsService.getGOODS_STATUS());
//        model.addAttribute("remainSeconds",goodsService.getLEFT_SECONDS());


        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
