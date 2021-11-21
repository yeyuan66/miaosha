package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.Goods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class GoodsVo extends Goods {

    private BigDecimal miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}
