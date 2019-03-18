package com.daniel.seckill.vo;

import com.daniel.seckill.model.OrderInfo;

/**
 * 订单详情VO
 *
 * @author DanielLin07
 * @date 2019/3/15 14:57
 */
public class OrderDetailVO {

    /**
     * 商品信息
     */
    private GoodsVO goodsVO;

    /**
     * 订单信息
     */
    private OrderInfo orderInfo;

    public GoodsVO getGoodsVO() {
        return goodsVO;
    }

    public void setGoodsVO(GoodsVO goodsVO) {
        this.goodsVO = goodsVO;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    @Override
    public String toString() {
        return "OrderDetailVO{" +
                "goodsVO=" + goodsVO +
                ", orderInfo=" + orderInfo +
                '}';
    }
}
