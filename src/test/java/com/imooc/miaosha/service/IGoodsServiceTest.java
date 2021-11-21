package com.imooc.miaosha.service;

import com.imooc.miaosha.MainApplicationTest;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class IGoodsServiceTest extends MainApplicationTest {

    @Autowired
    IGoodsService goodsService;
    @Test
    public void list() {
        List<GoodsVo> list = goodsService.list();
        log.info("GoodsVoList【】【】【】{}",list);
    }
}