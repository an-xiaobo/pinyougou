package com.pinyougou.service;
import java.util.List;
<<<<<<< HEAD
=======

import com.pinyougou.pojo.TbBrand;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
import com.pinyougou.pojo.TbSpecification;

import com.pinyougou.entity.PageResult;
import com.pinyougou.pojogroup.Specification;

/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbSpecification> findPage(int pageNum, int pageSize,TbSpecification specification);

	
	/**
	 * 修改
	 */
	public void update(Specification specification);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Specification getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 增加
	 * @param specification
	 */
<<<<<<< HEAD
	public void add(Specification specification);

=======
	public void add(TbSpecification specification);


	/**
	 * 查询当前登录商家的规格列表
	 * @return
	 */
	public List<TbSpecification> getBySellerId(String sellerId);


	/**
	 * 更新状态
	 * @param status
	 * @param Id
	 */
	public void updateStatus(String status, Long Id);
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3

}
