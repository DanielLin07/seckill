package com.daniel.seckill.service;

import com.daniel.seckill.dao.OrderDAO;
import com.daniel.seckill.model.OrderInfo;
import com.daniel.seckill.model.SeckillOrderInfo;
import com.daniel.seckill.model.User;
import com.daniel.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 订单相关的Service
 *
 * @author DanielLin07
 * @date 2018/11/18 16:58
 */
@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    /**
     * 根据订单Id获取订单
     *
     * @param orderId 订单Id
     * @return 成功则返回对应订单
     */
    public OrderInfo queryById(long orderId) {
        return orderDAO.queryById(orderId);
    }

    /**
     * 根据用户Id以及商品Id获取订单Id
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 成功则返回对应订单
     */
    public OrderInfo queryByUserIdAndGoodsId(long userId, long goodsId) {
        return orderDAO.queryByUserIdAndGoodsId(userId, goodsId);
    }

    /**
     * 根据用户Id以及商品Id获取秒杀订单Id
     *
     * @param userId  用户Id
     * @param goodsId 商品Id
     * @return 成功则返回对应秒杀订单
     */
    public SeckillOrderInfo querySeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderDAO.querySeckillOrderInfoByUserIdGoodsId(userId, goodsId);
    }

    /**
     * 秒杀减少库存后，创建新的订单以及新的秒杀订单
     *
     * @param user    用户
     * @param goodsVO 商品
     * @return 新的订单
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo createOrder(User user, GoodsVO goodsVO) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddressId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVO.getId());
        orderInfo.setGoodsName(goodsVO.getGoodsName());
        orderInfo.setGoodsPrice(goodsVO.getGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        // 插入新订单，获得新订单的Id
        long orderId = orderDAO.add(orderInfo);

        // 插入新的秒杀订单
        SeckillOrderInfo seckillOrderInfo = new SeckillOrderInfo();
        seckillOrderInfo.setGoodsId(goodsVO.getId());
        seckillOrderInfo.setUserId(user.getId());
        seckillOrderInfo.setOrderId(orderId);
        orderDAO.addSeckillOrderInfo(seckillOrderInfo);

        return orderInfo;
    }


}
