package com.pinyougou.show.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.service.SeckillGoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 秒杀商品 -- 请求控制
 * @author laozhu
 * @version $Id: SeackillGoodsController, v 0.1 2019/6/26 15:40  Exp$
 */
@RestController
@RequestMapping("/seckillgoods")
public class SeckillGoodsController {
    @Reference
   private SeckillGoodsService seckillGoodsService;
    /**
     * 返回全部 秒杀 商品列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbSeckillGoods> findAll(){
        return seckillGoodsService.findAll();
    };

}
