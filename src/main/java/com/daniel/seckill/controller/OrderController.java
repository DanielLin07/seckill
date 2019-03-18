package com.daniel.seckill.controller;

import com.daniel.seckill.common.CodeMsg;
import com.daniel.seckill.common.Result;
import com.daniel.seckill.common.ResultBuilder;
import com.daniel.seckill.model.OrderInfo;
import com.daniel.seckill.model.User;
import com.daniel.seckill.service.GoodsService;
import com.daniel.seckill.service.OrderService;
import com.daniel.seckill.service.RedisService;
import com.daniel.seckill.service.UserService;
import com.daniel.seckill.vo.GoodsVO;
import com.daniel.seckill.vo.OrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单相关的Controller
 *
 * @author DanielLin07
 * @date 2019/1/19 16:27
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
    private UserService userService;
	
	@Autowired
    private RedisService redisService;
	
	@Autowired
    private OrderService orderService;
	
	@Autowired
    private GoodsService goodsService;
	
    @GetMapping("/detail")
    @ResponseBody
    public Result info(User user, @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return ResultBuilder.build(CodeMsg.SESSION_ERROR);
    	}
    	// 根据订单Id获取订单信息
    	OrderInfo orderInfo = orderService.queryById(orderId);
    	if(orderInfo == null) {
    		return ResultBuilder.build(CodeMsg.ORDER_NOT_EXIST);
    	}

    	long goodsId = orderInfo.getGoodsId();
    	GoodsVO goodsVO = goodsService.queryGoodsVOById(goodsId);
    	OrderDetailVO orderDetailVO = new OrderDetailVO();
        orderDetailVO.setGoodsVO(goodsVO);
        orderDetailVO.setOrderInfo(orderInfo);
    	return ResultBuilder.build(orderDetailVO);
    }
    
}
