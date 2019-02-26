package com.daniel.seckill.model;

/**
 * 秒杀订单实体类
 *
 * @author DanielLin07
 * @date 2018/11/18 11:31
 */
public class SeckillOrderInfo {

    /**
     * 秒杀订单Id
     */
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 商品Id
     */
    private Long goodsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
