package com.daniel.seckill.service;

import com.daniel.seckill.model.OrderInfo;
import com.daniel.seckill.model.User;
import com.daniel.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀核心Service
 *
 * @author DanielLin07
 * @date 2018/11/18 20:51
 */
@Service
public class SeckillService {
	
	@Autowired
	private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    /**
     * 执行秒杀的方法，先减库存，再下订单，最后写入秒杀订单
     *
     * @param user 用户
     * @param goodsVO 商品
     * @return 成功则返回对应商品
     */
    @Transactional(rollbackFor = Exception.class)
    public OrderInfo doSeckill(User user, GoodsVO goodsVO) {
        // 减少商品的库存
        goodsService.updateSeckillGoodsStock(goodsVO, -1);
        // 下订单，写入秒杀订单
        return orderService.createOrder(user, goodsVO);
    }

}
