package com.daniel.seckill.controller;

import com.daniel.seckill.common.CodeMsg;
import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultBuilder;
import com.daniel.seckill.model.OrderInfo;
import com.daniel.seckill.model.SeckillOrderInfo;
import com.daniel.seckill.model.User;
import com.daniel.seckill.service.GoodsService;
import com.daniel.seckill.service.OrderService;
import com.daniel.seckill.service.SeckillService;
import com.daniel.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 秒杀功能的Controller
 *
 * @author DanielLin07
 * @date 2019/1/19 16:27
 */
@Controller
@RequestMapping("seckill")
public class SeckillController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    /**
     * 执行秒杀操作
     * 优化前：1000 * 10 536QPS
     * 优化后：1000 * 10
     *
     * @param user    用户信息
     * @param goodsId 商品Id
     * @return 秒杀结果
     */
    @PostMapping("doSeckill")
    @ResponseBody
    public Result doSeckill(User user, @RequestParam("goodsId") long goodsId) {
        // 首先判断用户是否登录
        if (user == null) {
            return ResultBuilder.build(CodeMsg.SESSION_ERROR);
        }

        // 判断库存是否足够
        GoodsVO goodsVO = goodsService.queryGoodsVOById(goodsId);
        int stockCount = goodsVO.getStockCount();
        if (stockCount <= 0) {
            return ResultBuilder.build(CodeMsg.SECKILL_OVER);
        }

        // 判断用户是否重复秒杀
        SeckillOrderInfo seckillOrderInfo = orderService.querySeckillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (seckillOrderInfo != null) {
            return ResultBuilder.build(CodeMsg.SECKILL_REPEAT);
        }

        // 执行秒杀操作，先减库存，再下订单，最后再写入秒杀订单表
        OrderInfo orderInfo = seckillService.doSeckill(user, goodsVO);
        return orderInfo != null ? ResultBuilder.build(orderInfo) : ResultBuilder.build(CodeMsg.SECKILL_OVER);
    }

}
