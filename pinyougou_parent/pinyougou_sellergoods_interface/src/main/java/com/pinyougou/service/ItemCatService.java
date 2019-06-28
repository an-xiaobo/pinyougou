package com.pinyougou.service;
import java.util.List;
import com.pinyougou.pojo.TbItemCat;

import com.pinyougou.entity.PageResult;
<<<<<<< HEAD
=======
import com.pinyougou.pojo.TbSpecification;

>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface ItemCatService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbItemCat> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbItemCat> findPage(int pageNum, int pageSize,TbItemCat itemCat);
	
	
	/**
	 * 增加
	*/
	public void add(TbItemCat itemCat);
	
	
	/**
	 * 修改
	 */
	public void update(TbItemCat itemCat);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbItemCat getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 根据父ID查找商品分类列表
	 * @param parentId
	 * @return
	 */
	public List<TbItemCat> findByParentId(Long parentId);
<<<<<<< HEAD
=======

	/**
	 * 查询当前登录商家的规格列表
	 * @return
	 */
	public List<TbItemCat> getBySellerId(String sellerId);


	/**
	 * 更新状态
	 * @param status
	 * @param Id
	 */
	public void updateStatus(String status, Long Id);
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
