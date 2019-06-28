package com.pinyougou.seckill.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.entity.PageResult;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.seckill.service.SeckillGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * 业务逻辑实现
 * @author xiaobo
 *
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillGoods> findAll() {
		return seckillGoodsMapper.select(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize,TbSeckillGoods seckillGoods) {
		PageResult<TbSeckillGoods> result = new PageResult<TbSeckillGoods>();
        //设置分页条件
        PageHelper.startPage(pageNum, pageSize);

        //构建查询条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
		
		if(seckillGoods!=null){			
						//如果字段不为空
			if (seckillGoods.getTitle()!=null && seckillGoods.getTitle().length()>0) {
				criteria.andLike("title", "%" + seckillGoods.getTitle() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getSmallPic()!=null && seckillGoods.getSmallPic().length()>0) {
				criteria.andLike("smallPic", "%" + seckillGoods.getSmallPic() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getSellerId()!=null && seckillGoods.getSellerId().length()>0) {
				criteria.andLike("sellerId", "%" + seckillGoods.getSellerId() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getStatus()!=null && seckillGoods.getStatus().length()>0) {
				criteria.andLike("status", "%" + seckillGoods.getStatus() + "%");
			}
			//如果字段不为空
			if (seckillGoods.getIntroduction()!=null && seckillGoods.getIntroduction().length()>0) {
				criteria.andLike("introduction", "%" + seckillGoods.getIntroduction() + "%");
			}
	
		}

        //查询数据
        List<TbSeckillGoods> list = seckillGoodsMapper.selectByExample(example);
        //返回数据列表
        result.setList(list);

        //获取总页数
        PageInfo<TbSeckillGoods> info = new PageInfo<TbSeckillGoods>(list);
        result.setPages(info.getPages());
		
		return result;
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeckillGoods seckillGoods) {
		seckillGoodsMapper.insertSelective(seckillGoods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillGoods seckillGoods){
		seckillGoodsMapper.updateByPrimaryKeySelective(seckillGoods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillGoods getById(Long id){
		return seckillGoodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		//数组转list
        List longs = Arrays.asList(ids);
        //构建查询条件
        Example example = new Example(TbSeckillGoods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", longs);

        //跟据查询条件删除数据
        seckillGoodsMapper.deleteByExample(example);
	}

	/**
	 * 跟据id列表，更新状态
	 * @param ids
	 * @param status
	 */
    @Override
    public void updateStatus(Long[] ids, String status) {
        //要更新的内容
		TbSeckillGoods where = new TbSeckillGoods();
		where.setStatus(status);//审核状态

		//组装条件
		Example example = new Example(TbSeckillGoods.class);
		Example.Criteria criteria = example.createCriteria();
		List  longs = Arrays.asList(ids);
		criteria.andIn("id", longs);

		//执行更新
		seckillGoodsMapper.updateByExampleSelective(where, example);
	}

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询当前正在参与秒杀的商品
	 * @return
	 */
	@Override
	public List<TbSeckillGoods> findList() {
		//从redis中查找商品列表
		return  redisTemplate.boundHashOps("seckillGoods").values();
	}

	/**
	 * 跟据秒杀商品的Id获取时间差和库存信息
	 * @param goodsId
	 * @return
	 */
    @Override
    public Map<String, Object> getGoodsInfoById(Long goodsId) {
    	//构建返回参数
		Map<String,Object> map = new HashMap<>();

    	//从redis中取出库存数
		Long count = (Long) redisTemplate.boundHashOps("seckillStockCount").increment(goodsId, 0);
		map.put("count", count);

		//取出商品信息
		TbSeckillGoods seckillGoods = (TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(goodsId);
		//计算时间差-秒
		long allsecond = (seckillGoods.getEndTime().getTime()- System.currentTimeMillis())/1000;
		map.put("allsecond", allsecond);
		//返回
		return map;
    }

}
