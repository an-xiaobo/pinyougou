package com.pinyougou.user.service;

import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojogroup.UserOrder;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 注册方法
     * @param user
     */
    public void add(TbUser user);

    /**
     * 生成短信验证码
     * @param phone
     */
    public void createSmsCode(String phone);

    boolean checkSmsCode(String phone, String code);


    /**
     * 根据登录用户id查询订单和对应商品
     * @param userId
     * @return
     */
    public List<UserOrder> findOrderByUserId(String userId);

    /**
     * 根据登录用户id查询信息
     * @param userId
     * @return
     */
    public Map<String,Object> getUserInfoByUserId(String userId);


    /**
     * 修改用户
     * @param userInfo
     */
    public void changeUserInfo(TbUser userInfo);

    /**
     * 根据登录用户查询收藏商品
     * @param username
     * @return
     */
    public List<TbItem> findGoodsCollect(String username);
}
