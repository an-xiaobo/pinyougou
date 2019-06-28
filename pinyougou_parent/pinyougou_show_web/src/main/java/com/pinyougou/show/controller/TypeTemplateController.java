package com.pinyougou.show.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.ResultInfo;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 请求处理器
 * @author Steven
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll(){			
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * 分页查询数据
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int pageNo,int pageSize,@RequestBody TbTypeTemplate typeTemplate){			
		return typeTemplateService.findPage(pageNo, pageSize,typeTemplate);
	}
	
	/**
	 * 增加
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public ResultInfo add(@RequestBody TbTypeTemplate typeTemplate){
		try {
			//从Security中获取用户名
			String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
			// 追加商家id
			typeTemplate.setSellerId(sellerId);
			typeTemplate.setStatus("0");
			typeTemplateService.add(typeTemplate);
			return new ResultInfo(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public ResultInfo update(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
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
	public TbTypeTemplate getById(Long id){
		return typeTemplateService.getById(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public ResultInfo delete(Long [] ids){
		try {
			typeTemplateService.delete(ids);
			return new ResultInfo(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "删除失败");
		}
	}

	/**
	 * 根据模板id查找规格列表
	 * @param templateId
	 * @return
	 */
	@RequestMapping("/findSpecList")
	public List<Map> findSpecList(Long templateId){
		return typeTemplateService.findSpecList(templateId);
	}

	/**
	 * 查询当前登录商家的模板列表
	 * @return
	 */
	@RequestMapping("/findTemplate")
	public List<TbTypeTemplate> findTemplate(){

		//从Security中获取用户名
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();

		// 根据商家查询模板列表
		return typeTemplateService.getBySellerId(sellerId);

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
			typeTemplateService.updateStatus(status,id);
			return new ResultInfo(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultInfo(false, "修改失败");
		}
	}
}
