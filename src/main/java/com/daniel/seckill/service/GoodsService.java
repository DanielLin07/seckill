package com.daniel.seckill.service;

import com.daniel.seckill.dao.GoodsDAO;
import com.daniel.seckill.model.SeckillGoods;
import com.daniel.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品相关的Service
 *
 * @author DanielLin07
 * @date 2018/11/18 16:53
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    /**
     * 根据商品Id获取对应商品
     *
     * @param goodsId 商品Id
     * @return 成功则返回对应商品
     */
    public GoodsVO queryGoodsVOById(long goodsId) {
        return goodsDAO.queryGoodsVOById(goodsId);
    }

    /**
     * 获取所有商品
     *
     * @return 所有商品
     */
    public List<GoodsVO> queryListGoodsVO() {
        return goodsDAO.queryListGoodsVO();
    }

    /**
     * 更新秒杀商品的库存
     *
     * @param goodsVO 秒杀商品
     * @param num     库存改变量
     * @return 被修改的行数
     */
    public Integer updateSeckillGoodsStock(GoodsVO goodsVO, int num) {
        return goodsDAO.updateSeckillGoodsStock(goodsVO.getId(), num);
    }

}
