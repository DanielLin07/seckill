package com.daniel.seckill.model;

import java.util.Date;

/**
 * 秒杀商品实体类
 *
 * @author DanielLin07
 * @date 2018/11/18 11:30
 */
public class SeckillGoods {

    /**
     * 秒杀商品Id
     */
    private Long id;

    /**
     * 商品Id
     */
    private Long goodsId;

    /**
     * 商品库存，-1表示没有限制
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
