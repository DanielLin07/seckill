package com.daniel.seckill.redis;

/**
 * 商品Redis-Key
 *
 * @author DanielLin07
 * @date 2018/11/13 22:05
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "goodsList");

    public static GoodsKey getGoodsDetail = new GoodsKey(60, "goodsDetail");
}
