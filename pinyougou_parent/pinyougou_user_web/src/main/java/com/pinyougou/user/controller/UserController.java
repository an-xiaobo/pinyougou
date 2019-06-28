package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.utils.PhoneFormatCheckUtils;
import com.pinyougou.entity.ResultInfo;
<<<<<<< HEAD
import com.pinyougou.pojo.TbUser;
import com.pinyougou.user.service.UserService;
=======
import com.pinyougou.pojo.TbAddress;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojogroup.UserOrder;
import com.pinyougou.user.service.AddressService;
import com.pinyougou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
=======
import java.util.List;
import java.util.Map;

>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
@RestController
@RequestMapping("/user")
public class UserController {

<<<<<<< HEAD
    @Reference
    private UserService userService;

=======

    @Reference
    private UserService userService;

    @Reference
    private AddressService addressService;

>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
    @RequestMapping("/add")
    public ResultInfo add(@RequestBody TbUser user,String code){
        try {
            boolean checkSmsCode = userService.checkSmsCode(user.getPhone(),code);
            if (checkSmsCode == false){
                return new ResultInfo(false, "验证码输入错误!");
            }
            userService.add(user);
            return new ResultInfo(true, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, "注册失败");
        }
    }

    @RequestMapping("/sendCode")
    public ResultInfo sendCode(String phone){
        if (!PhoneFormatCheckUtils.isPhoneLegal(phone)){
            return new ResultInfo(false, "手机号不符合要求!");
        }
        try {
            userService.createSmsCode(phone);
            return new ResultInfo(true, "验证码发送成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false, "验证码发送失败!");
        }
    }
<<<<<<< HEAD
=======

    /**
     * 根据登录用户id查询订单和对应商品
     *
     * @return
     */
    @RequestMapping("/findOrderByUserId")
    public List<UserOrder> findOrderByUserId(){
        //获取登录用户
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserOrder> userOrderList = userService.findOrderByUserId(userId);

        return userOrderList;

    }

    /**
     * 根据登录用户id查询信息
     * @return
     */
    @RequestMapping("/getUserInfoByUserId")
    public Map<String, Object> getUserInfoByUserId(){
        //获取登录用户
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<String, Object> map = userService.getUserInfoByUserId(userId);
        return map;

    }



    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    @RequestMapping("/changeUserInfo")
    public ResultInfo changeUserInfo(@RequestBody TbUser userInfo){
        //获取登录用户
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = userService.getUserInfoByUserId(userId);
        TbUser user = (TbUser) map.get("user");

        userInfo.setUsername(userId);
        userInfo.setId(user.getId());

        userService.changeUserInfo(userInfo);


        return new ResultInfo(true,"修改成功");
    }

    /**
     * 修改默认地址的省市区
     * @param address
     * @return
     */
    @RequestMapping("/changeAddress")
    public ResultInfo changeAddress(@RequestBody TbAddress address){
        //获取登录用户
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = userService.getUserInfoByUserId(userId);
        TbAddress address1 = (TbAddress) map.get("address");

        address.setIsDefault("1");
        address.setId(address1.getId());
        addressService.update(address);
        return new ResultInfo(true,"修改成功");
    }

    /**
     * 增加收货地址
     * @param address
     * @return
     */
    @RequestMapping("/addAddress")
    public ResultInfo addAddress(@RequestBody TbAddress address){
        try {
            //获取登录用户
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();


            address.setUserId(userId);
            addressService.add(address);

            return new ResultInfo(true,"新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,"新增失败");
        }

    }

    /**
     * 删除收货地址
     * @param
     * @return
     */
    @RequestMapping("/deleteAddressById")
    public ResultInfo deleteAddressById(Long id){
        try {

            addressService.delete(id);

            return new ResultInfo(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,"删除失败");
        }

    }

    /**
     * 修改收货地址
     * @param address
     * @return
     */
    @RequestMapping("/modifyAddress")
    public ResultInfo modifyAddress(@RequestBody TbAddress address){
        try {

            addressService.update(address);

            return new ResultInfo(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(false,"修改失败");
        }

    }

    /**
     * 收货地址--回显
     * @param
     * @return
     */
    @RequestMapping("/findAddressById")
    public TbAddress findAddressById(Long id){
        TbAddress address = addressService.getById(id);
        return address;

    }

    /**
     * 查询用户收藏
     * @param
     * @return
     */
    @RequestMapping("/findGoodsCollect")
    public List findGoodsCollect(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TbItem> goodsCollect = userService.findGoodsCollect(username);

        return goodsCollect;

    }


>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
