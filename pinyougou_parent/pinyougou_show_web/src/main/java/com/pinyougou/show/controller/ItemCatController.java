package com.pinyougou.show.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultInfo;
import com.pinyougou.pojo.TbItemCat;
<<<<<<< HEAD
import com.pinyougou.service.ItemCatService;
=======
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.ItemCatService;
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

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
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int pageNo,int pageSize,@RequestBody TbItemCat itemCat){			
		return itemCatService.findPage(pageNo, pageSize,itemCat);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/add")
	public ResultInfo add(@RequestBody TbItemCat itemCat){
		try {
<<<<<<< HEAD
=======
			//从Security中获取用户名
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			// 追加商家id
			itemCat.setSellerId(sellerId);
			itemCat.setStatus("0");
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
			itemCatService.add(itemCat);
			return new ResultInfo(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/update")
	public ResultInfo update(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.update(itemCat);
			return new ResultInfo(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/getById")
	public TbItemCat getById(Long id){
		return itemCatService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultInfo delete(Long [] ids){
		try {
			itemCatService.delete(ids);
			return new ResultInfo(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "删除失败");
		}
	}

	/**
	 * 根据父ID查找商品分类列表
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/findByParentId")
	public List<TbItemCat> findByParentId(Long parentId){
		return itemCatService.findByParentId(parentId);
	}
<<<<<<< HEAD
	
=======

	/**
	 * 查询当前登录商家的分类列表
	 * @return
	 */
	@RequestMapping("/findItemCat")
	public List<TbItemCat> findBrand(){

		//从Security中获取用户名
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

		// 根据商家查询分类列表
		return itemCatService.getBySellerId(sellerId);

	}

	/**
	 * 更新状态
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public ResultInfo updateStatus(String status,Long id){
		try {
			// 更新状态
			itemCatService.updateStatus(status,id);
			return new ResultInfo(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "修改失败");
		}
	}
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
