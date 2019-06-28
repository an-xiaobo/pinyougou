package com.pinyougou.service;
import java.util.List;
import com.pinyougou.pojo.TbUser;

import com.pinyougou.entity.PageResult;
/**
 * 业务逻辑接口
 * @author Steven
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
     * 分页查询列表
     * @return
     */
    public PageResult<TbUser> findPage(int pageNum, int pageSize,TbUser user);
	
	
	/**
	 * 增加
	*/
	public void add(TbUser user);
	
	
	/**
	 * 修改
	 */
	public void update(TbUser user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbUser getById(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long [] ids);

<<<<<<< HEAD
	
=======

    void updateStatus(Integer id, String status);

	Integer countUserSum();

	//查活跃用户，24小时之内登录过的
	Integer countActiveUser();

>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
