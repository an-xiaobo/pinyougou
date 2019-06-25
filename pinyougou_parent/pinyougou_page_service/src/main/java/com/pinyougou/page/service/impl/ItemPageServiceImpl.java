package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;

    @Value("${pagedir}")
    private String pagedir;

    @Override
    public boolean getItemHtml(Long goodsId) {
        try {
            //读取模板对象
            Configuration cfg = freeMarkerConfigurer.getConfiguration();
            Template template = cfg.getTemplate("item.ftl");

            //构建数据模型对象
            Map map = new HashMap();

            //1.查询商品基本信息
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            map.put("goods",goods);

            //2.查询商品描述信息
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            map.put("goodsDesc", goodsDesc);

            //3.查询商品分类
            String category1Id = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String category2Id = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String category3Id = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            map.put("category1Id", category1Id);
            map.put("category2Id", category2Id);
            map.put("category3Id", category3Id);

            //4.查询商品sku信息
            Example example = new Example(TbItem.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("goodsId", goodsId);//指定spu-id
            criteria.andEqualTo("status", "1");//查询有效的sku
            example.setOrderByClause("isDefault desc");//按默认降序,为了第一个选中默认
            List<TbItem> itemList = itemMapper.selectByExample(example);
            map.put("itemList", itemList);

            //输出静态页面
            Writer out = new FileWriter(pagedir + goodsId + ".html");
            template.process(map, out);
            //关闭资源
            out.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
