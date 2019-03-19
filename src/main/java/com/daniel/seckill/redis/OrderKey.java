package com.daniel.seckill.redis;

/**
 * 订单Redis-Key
 *
 * @author DanielLin07
 * @date 2018/11/13 22:12
 */
public class OrderKey extends BasePrefix {

    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSeckillOrderByUserIdGoodsId = new OrderKey("seckillOrderUserIdGoodsId");
}
