package com.pinyougou.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/conweb")
@RestController
public class IdController {

    @Reference
    private ItemCatService itemCatService;


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbItemCat> findAll(){
        return itemCatService.findAll();
    }

    /**
     * 跟据父ID查询商品分类列表
     * @param parentId 父id
     * @return
     */
    @RequestMapping("findByParentId")
    public List<TbItemCat> findByParentId(Long parentId){
        return itemCatService.findByParentId(parentId);
    }

}
