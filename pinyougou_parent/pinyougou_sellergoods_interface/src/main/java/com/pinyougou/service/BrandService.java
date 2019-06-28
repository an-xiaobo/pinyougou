package com.pinyougou.service;
import java.util.List;
import com.pinyougou.pojo.TbBrand;

import com.pinyougou.entity.PageResult;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface BrandService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbBrand> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbBrand> findPage(int pageNum, int pageSize,TbBrand brand);
	
	
	/**
	 * 增加
	*/
	public void add(TbBrand brand);
	
	
	/**
	 * 修改
	 */
	public void update(TbBrand brand);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbBrand getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

<<<<<<< HEAD
	
=======

	/**
	 * 查询当前登录商家的品牌列表
	 * @return
	 */
	public List<TbBrand> getBySellerId(String sellerId);


	/**
	 * 更新状态
	 * @param status
	 * @param Id
	 */
	public void updateStatus(String status, Long Id);
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
