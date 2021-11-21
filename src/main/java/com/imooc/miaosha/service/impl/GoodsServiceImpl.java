package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.MiaoshaGoodsMapper;
import com.imooc.miaosha.enums.GoodsStatusEnum;
import com.imooc.miaosha.service.IGoodsService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

      private Integer GOODS_STATUS;
      private Integer LEFT_SECONDS;

    @Autowired
    MiaoshaGoodsMapper miaoshaGoodsMapper;
    @Override
    public List<GoodsVo> list() {

        return miaoshaGoodsMapper.list();
    }

    @Override
    public GoodsVo getVoById(Long goodsId) {
        GoodsVo goodsVo = miaoshaGoodsMapper.getVoById(goodsId);

        long start = goodsVo.getStartDate().getTime();
        long end = goodsVo.getEndDate().getTime();

        long current = System.currentTimeMillis();

        if(current<start){
            GOODS_STATUS = GoodsStatusEnum.NOT_BEGIN.getCode();
            LEFT_SECONDS = Math.toIntExact(start - end);
        }
        else if(end<current){
            GOODS_STATUS = GoodsStatusEnum.END.getCode();
            LEFT_SECONDS = -1;
        }
        else {
            GOODS_STATUS = GoodsStatusEnum.ON_MIAO.getCode();
            LEFT_SECONDS = Math.toIntExact(current - start);
        }

        return goodsVo;
    }

    @Override
    public void decr_Stock(Long goodsId) {
        try {
            miaoshaGoodsMapper.updateStock(goodsId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Integer getGOODS_STATUS() {
        return GOODS_STATUS;
    }

    public Integer getLEFT_SECONDS() {
        return LEFT_SECONDS;
    }
}
