package com.daniel.seckill.controller;

import com.daniel.seckill.model.User;
import com.daniel.seckill.redis.GoodsKey;
import com.daniel.seckill.redis.JedisAdapter;
import com.daniel.seckill.service.GoodsService;
import com.daniel.seckill.service.RedisService;
import com.daniel.seckill.vo.GoodsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品相关的Controller
 *
 * @author DanielLin07
 * @date 2019/1/19 16:27
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品列表页
     *
     * @param model model
     * @param user  用户信息
     * @return 跳转至商品列表页
     */
    @RequestMapping(value = "toList", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, User user) {
        model.addAttribute("user", user);

        // 首先去Redis中查页面缓存，如果存在缓存，则直接返回
        String html = redisService.get(GoodsKey.getGoodsList.getPrefix());
        if (StringUtils.isNotEmpty(html)) {
            return html;
        }

        // 如果不存在缓存，则查询商品列表页并进行手动渲染
        List<GoodsVO> goodsVOList = goodsService.queryListGoodsVO();
        model.addAttribute("goodsVOList", goodsVOList);
        IWebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        // 执行手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        // 将页面缓存到Redis当中
        if (StringUtils.isNotEmpty(html)) {
            redisService.setex(GoodsKey.getGoodsList, html);
        }
        return html;
    }

    /**
     * 商品细节页
     *
     * @param model   model
     * @param user    用户信息
     * @param goodsId 商品Id
     * @return 跳转至商品细节页
     */
    @RequestMapping(value = "toDetail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request, HttpServletResponse response,
                           Model model, User user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);

        // 首先去Redis中查页面缓存，如果存在缓存，则直接返回
        String html = redisService.get(GoodsKey.getGoodsDetail, goodsId);
        if (StringUtils.isNotEmpty(html)) {
            return html;
        }

        // 根据商品Id查询商品详情
        GoodsVO goodsVO = goodsService.queryGoodsVOById(goodsId);
        model.addAttribute("goodsVO", goodsVO);

        long startTime = goodsVO.getStartDate().getTime();
        long endTime = goodsVO.getEndDate().getTime();
        long currentTime = System.currentTimeMillis();

        // 记录秒杀状态以及倒计时
        long seckillStatus = 0;
        long remainSeconds = 0;
        // 秒杀还未开始，处于倒计时状态
        if (currentTime < startTime) {
            remainSeconds = (startTime - currentTime) / 1000;
            // 秒杀已经结束
        } else if (currentTime > endTime) {
            seckillStatus = 2;
            remainSeconds = -1;
            // 秒杀正在进行中
        } else {
            seckillStatus = 1;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        IWebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        // 执行手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        // 将页面缓存到Redis当中
        if (StringUtils.isNotEmpty(html)) {
            redisService.setex(GoodsKey.getGoodsDetail, goodsId, html);
        }
        return html;
    }
}
