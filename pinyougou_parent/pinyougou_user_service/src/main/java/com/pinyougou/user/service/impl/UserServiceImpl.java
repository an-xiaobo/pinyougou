package com.pinyougou.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
<<<<<<< HEAD
import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;
=======
import com.github.abel533.entity.Example;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.pojogroup.UserOrder;
import com.pinyougou.user.service.AddressService;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
import com.pinyougou.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

<<<<<<< HEAD
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
=======
import java.util.*;
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultMQProducer producer;

<<<<<<< HEAD
=======
    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbAddressMapper addressMapper;

    @Autowired
    TbGoodCollectMapper goodCollectMapper;

    @Autowired TbItemMapper itemMapper;

>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
    @Override
    public void add(TbUser user) {
        user.setCreated(new Date());//创建日期
        user.setUpdated(user.getCreated());//修改日期
        String password = DigestUtils.md5Hex(user.getPassword());//对密码加密
        user.setPassword(password);
        userMapper.insertSelective(user);
    }

    @Override
    public void createSmsCode(String phone) {
        //生成六位随机数
        String code = (long)(Math.random() * 1000000)+"";
        //存储以user_mobile_+手机号为key
        BoundValueOperations valueOperations = redisTemplate.boundValueOps("user_mobile" + phone);
        //保存验证码
        valueOperations.set(code);
        //设置有效时间为  1分钟
        valueOperations.expire(1, TimeUnit.MINUTES);

        //发送rockerMq消息
        try {
            //创建消息对象 String mobile, String signName, String templateCode, String templateParam
            Map<String,String> map = new HashMap<>();
            map.put("mobile", phone);
            map.put("signName", "AnXiaoBo");
            map.put("templateCode", "SMS_167401894");
            map.put("templateParam", "{\"code\":"+code+"}");
            //将Map转换为String
            String json = JSON.toJSONString(map);

            Message message = new Message(
                    "topic-sms",
                    "tags-sms-test",
                    "keys-sms-test",
                    json.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //发送消息
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean checkSmsCode(String phone, String code) {
        //读取验证码
        String smsCode = (String) redisTemplate.boundValueOps("user_mobile" + phone).get();
        //对比用户输入的验证码是否一样
        return code.equals(smsCode);
    }
<<<<<<< HEAD
=======

    /**
     * 根据登录用户id查询订单和对应商品
     * @param userId
     * @return
     */
    @Override
    public List<UserOrder> findOrderByUserId(String userId) {

        List<UserOrder> list = new ArrayList();

        //查询订单
        TbOrder tbOrder = new TbOrder();
        tbOrder.setUserId(userId);
        List<TbOrder> tbOrders = orderMapper.select(tbOrder);

        //查询每个订单对应商品
        for (TbOrder order : tbOrders) {
            TbOrderItem tbOrderItem = new TbOrderItem();
            tbOrderItem.setOrderId(order.getOrderId());
            List<TbOrderItem> items = orderItemMapper.select(tbOrderItem);

            //创建每个订单对象,包含每个订单对应的商品
            UserOrder userOrder = new UserOrder();
            //封装对象
            userOrder.setOrder(order);
            userOrder.setOrderItems(items);

            list.add(userOrder);
        }

        //返回
        return list;
    }

    /**
     * 根据登录用户id查询信息
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> getUserInfoByUserId(String userId) {
        //查询用户信息
        TbUser tbUser = new TbUser();
        tbUser.setUsername(userId);
        TbUser user = userMapper.selectOne(tbUser);

        //查询默认地址
        TbAddress tbAddress = new TbAddress();
        tbAddress.setUserId(userId);
        tbAddress.setIsDefault("1");
        TbAddress address = addressMapper.selectOne(tbAddress);

        //查询所有收货地址
        TbAddress tbAddress2 = new TbAddress();
        tbAddress2.setUserId(userId);
        List<TbAddress> address2 = addressMapper.select(tbAddress2);

        //封装对象
        HashMap<String,Object> map = new HashMap();
        map.put("user",user);
        map.put("address",address);
        map.put("address2",address2);

        return map;
    }

    /**
     * 修改用户
     * @param userInfo
     */
    @Override
    public void changeUserInfo(TbUser userInfo) {
        userMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 根据登录用户查询收藏商品
     * @param username
     * @return
     */
    @Override
    public List<TbItem> findGoodsCollect(String username) {
        //查询该用户所收藏的商品列表
        TbGoodCollect tbGoodCollect = new TbGoodCollect();
        tbGoodCollect.setUsername(username);
        List<TbGoodCollect> goodCollects = goodCollectMapper.select(tbGoodCollect);
        List goodCollectsId = new ArrayList();
        for (TbGoodCollect goodCollect : goodCollects) {
            goodCollectsId.add(goodCollect.getGoodId());
        }

        //根据查出的商品ID列表查出商品列表
        Example example = new Example(TbItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",goodCollectsId);

        List<TbItem> tbItems = itemMapper.selectByExample(example);

        return tbItems;
    }
>>>>>>> 4b9b0fb0864012a9a701190321b801c95518e1e3
}
