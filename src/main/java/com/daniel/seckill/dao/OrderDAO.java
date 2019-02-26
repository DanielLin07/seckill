package com.daniel.seckill.dao;

import com.daniel.seckill.model.OrderInfo;
import com.daniel.seckill.model.SeckillOrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 订单相关DAO
 *
 * @author DanielLin07
 * @date 2018/11/18 17:21
 */
@Mapper
@Repository
public interface OrderDAO {

    /**
     * 根据用户Id以及商品Id获取订单Id
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 成功则返回对应订单
     */
    OrderInfo queryByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * 根据用户Id以及商品Id获取秒杀订单Id
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 成功则返回对应秒杀订单
     */
    SeckillOrderInfo querySeckillOrderInfoByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /**
     * 插入新订单
     *
     * @param orderInfo 订单
     * @return 新订单的主键Id
     */
    Long add(OrderInfo orderInfo);

    /**
     * 插入新的秒杀订单
     *
     * @param seckillOrderInfo 秒杀订单
     * @return 新秒杀订单的主键Id
     */
    Long addSeckillOrderInfo(SeckillOrderInfo seckillOrderInfo);

}
