package com.imooc.miaosha.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class MiaoshaGoods {
    private Long id;

    private Long goodsId;

    private BigDecimal miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;


}