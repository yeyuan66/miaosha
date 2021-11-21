package com.imooc.miaosha.redis;

public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireSecond, String prefix) {
        super(expireSecond, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60,"gd");
    public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0, "gs");}
