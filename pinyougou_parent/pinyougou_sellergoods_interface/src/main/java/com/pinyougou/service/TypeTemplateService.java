package com.pinyougou.service;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
=======
import com.pinyougou.pojo.TbSpecification;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
import com.pinyougou.pojo.TbTypeTemplate;

import com.pinyougou.entity.PageResult;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface TypeTemplateService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbTypeTemplate> findPage(int pageNum, int pageSize,TbTypeTemplate typeTemplate);
	
	
	/**
	 * 增加
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * 修改
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbTypeTemplate getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 根据模板id查找规格列表
	 * @param templateId
	 * @return
	 */
	public List<Map> findSpecList(Long templateId);
<<<<<<< HEAD
	
=======

	/**
	 * 查询当前登录商家的规格列表
	 * @return
	 */
	public List<TbTypeTemplate> getBySellerId(String sellerId);


	/**
	 * 更新状态
	 * @param status
	 * @param Id
	 */
	public void updateStatus(String status, Long Id);
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
