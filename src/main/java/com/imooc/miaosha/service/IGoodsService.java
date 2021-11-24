package com.imooc.miaosha.service;

import com.imooc.miaosha.vo.GoodsVo;

import java.util.List;

public interface IGoodsService {
     Integer getGOODS_STATUS() ;

     Integer getLEFT_SECONDS() ;
    List<GoodsVo> list();
    GoodsVo getVoById(Long goodsId);

     GoodsVo getGoodsVoByGoodsId(long goodsId);
    void decr_Stock(Long goodsId);
}
