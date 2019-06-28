package com.pinyougou.show.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultInfo;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;


	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){			
		return brandService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int pageNo,int pageSize,@RequestBody TbBrand brand){			
		return brandService.findPage(pageNo, pageSize,brand);
	}
	
	/**
	 * 增加
	 * @param brand
	 * @return
	 */
	@RequestMapping("/add")
	public ResultInfo add(@RequestBody TbBrand brand){
		try {
			//从Security中获取用户名
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			// 追加商家id
			brand.setSellerId(sellerId);
			brand.setStatus("0");

			brandService.add(brand);
			return new ResultInfo(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param brand
	 * @return
	 */
	@RequestMapping("/update")
	public ResultInfo update(@RequestBody TbBrand brand){
		try {
			brandService.update(brand);
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
	public TbBrand getById(Long id){
		return brandService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultInfo delete(Long [] ids){
		try {
			brandService.delete(ids);
			return new ResultInfo(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "删除失败");
		}
	}

	/**
	 * 查询当前登录商家的品牌列表
	 * @return
	 */
	@RequestMapping("/findBrand")
	public List<TbBrand> findBrand(){

		//从Security中获取用户名
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

		// 根据商家查询品牌列表
		return brandService.getBySellerId(sellerId);

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
			brandService.updateStatus(status,id);
			return new ResultInfo(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "修改失败");
		}
	}
}
