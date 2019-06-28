package com.pinyougou.show.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultInfo;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.service.SpecificationService;
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
@RequestMapping("/specification")
public class SpecificationController {

	@Reference
	private SpecificationService specificationService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll(){			
		return specificationService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int pageNo,int pageSize,@RequestBody TbSpecification specification){			
		return specificationService.findPage(pageNo, pageSize,specification);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@RequestMapping("/add")
	public ResultInfo add(@RequestBody TbSpecification specification){
		try {
			//从Security中获取用户名
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			// 追加商家id
			specification.setSellerId(sellerId);
			specification.setStatus("0");

			specificationService.add(specification);
			return new ResultInfo(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param specification
	 * @return
	 */
	@RequestMapping("/update")
	public ResultInfo update(@RequestBody Specification specification){
		try {
			specificationService.update(specification);
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
	public Specification getById(Long id){
		return specificationService.getById(id);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultInfo delete(Long [] ids){
		try {
			specificationService.delete(ids);
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
	@RequestMapping("/findSpec")
	public List<TbSpecification> findBrand(){

		//从Security中获取用户名
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

		// 根据商家查询品牌列表
		return specificationService.getBySellerId(sellerId);

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
			specificationService.updateStatus(status,id);
			return new ResultInfo(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "修改失败");
		}
	}
	
}
