package com.daniel.seckill.dao;

import com.daniel.seckill.model.SeckillGoods;
import com.daniel.seckill.vo.GoodsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品相关DAO
 *
 * @author DanielLin07
 * @date 2018/11/18 17:21
 */
@Mapper
@Repository
public interface GoodsDAO {

    /**
     * 根据商品Id获取对应商品
     *
     * @param id 商品Id
     * @return 成功则返回对应商品
     */
    GoodsVO queryGoodsVOById(long id);

    /**
     * 获取所有商品
     *
     * @return 所有商品
     */
    List<GoodsVO> queryListGoodsVO();

    /**
     * 更新秒杀商品的库存
     *
     * @param goodsId 秒杀商品Id
     * @param num     库存改变量
     * @return 被修改的行数
     */
    Integer updateSeckillGoodsStock(@Param("goodsId") long goodsId, @Param("num") int num);

}
