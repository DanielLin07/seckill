package com.daniel.seckill.vo;

import com.daniel.seckill.model.User;

/**
 * 商品详情VO
 *
 * @author DanielLin07
 * @date 2019/3/15 14:57
 */
public class GoodsDetailVO {

    /**
     * 秒杀状态
     */
	private Integer seckillStatus;

    /**
     * 剩余秒数
     */
	private Integer remainSeconds;

    /**
     * 商品信息
     */
	private GoodsVO goodsVO ;

    /**
     * 用户信息
     */
	private User user;

    public Integer getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(Integer seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public Integer getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(Integer remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVO getGoodsVO() {
        return goodsVO;
    }

    public void setGoodsVO(GoodsVO goodsVO) {
        this.goodsVO = goodsVO;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GoodsDetailVO{" +
                "seckillStatus=" + seckillStatus +
                ", remainSeconds=" + remainSeconds +
                ", goodsVO=" + goodsVO +
                ", user=" + user +
                '}';
    }
}
