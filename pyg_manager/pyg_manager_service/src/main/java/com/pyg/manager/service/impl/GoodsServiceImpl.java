package com.pyg.manager.service.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.utils.PygResult;
import com.pyg.vo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbGoodsExample.Criteria;
import com.pyg.manager.service.GoodsService;

import com.pyg.utils.PageResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper tbGoodsDescMapper;

	@Autowired
	private TbBrandMapper brandMapper;

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private TbItemMapper itemMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
    @Transactional
	public PygResult add(Goods goods) {

            // 保存货品表数据
            // 获取货品对象
            TbGoods tbGoods = goods.getGoods();
            tbGoods.setAuditStatus("0");
            // 保存货品,返回主键
            goodsMapper.insertSelective(tbGoods);
            int x = 1/0;
            // 再保存货品描述表
            // 获取货品描述对象
            TbGoodsDesc goodsDesc = goods.getGoodsDesc();
            // 设置外键
            goodsDesc.setGoodsId(tbGoods.getId());
            // 保存
            tbGoodsDescMapper.insertSelective(goodsDesc);
            // 保存sku
            saveItemList(goods);
            return new PygResult(true, "保存成功");


    }




    private void saveItemList(Goods goods){
        if("1".equals(goods.getGoods().getIsEnableSpec())){
            // 在保存商品sku列表
            // 获取商品sku列表对象
            List<TbItem> list = goods.getItemList();
            // 循环sku列表对象
            for (TbItem item : list) {

                // 定义商品名称
                // 华为 移动4G,16G
                String goodsName = "";
                // 获取spec对象值
                // {spec:{"网络":"电信2G"}
                Map<String, Object> specMap = JSON.parseObject(item.getSpec(),Map.class);

                // 循环specmap对象
                for (String key : specMap.keySet()) {
                    // 组合商品名称
                    goodsName += specMap.get(key);

                }

                // 创建商品对象
                // 设置商品相关属性值
                item.setGoodsId(goods.getGoods().getId());
                // 组合商品名称
                item.setTitle(goods.getGoods().getGoodsName() + goodsName);
                // 从商品描述对象中获取图片数据
                String itemImages = goods.getGoodsDesc().getItemImages();

                Map map1 = null;
                // 判断描述对象中图片是否存在
                if (itemImages != null && !"".equals(itemImages)) {
                    // [{"color":"白色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmNXEWAWuHOAAjlKdWCzvg949.jpg"},{"color":"黑色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmNXEuAB_ujAAETwD7A1Is158.jpg"},{"color":"蓝色","url":"http://192.168.25.133/group1/M00/00/00/wKgZhVmNXFWANtjTAAFa4hmtWek619.jpg"}]
                    List<Map> images = JSON.parseArray(itemImages, Map.class);
                    // 获取第一个对象
                    map1 = images.get(0);
                }
                item.setImage((String) map1.get("url"));


                //通过货品获取品牌数据
                TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());

                //保存品牌
                item.setBrand(brand.getName());

                //查询分类对象
                TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());
                //
                item.setCategory(itemCat.getName());
                //分类id
                item.setCategoryid(itemCat.getId());

                item.setSellerId(goods.getGoods().getSellerId());

                //设置时间
                Date date = new Date();
                item.setCreateTime(date);
                item.setUpdateTime(date);

                //保存
                itemMapper.insertSelective(item);

            }
        }else{
            // 不启用规格则添加一个SKU
            TbItem item = new TbItem();
            item.setTitle(goods.getGoods().getGoodsName());
            item.setPrice(goods.getGoods().getPrice());
            item.setStatus("1");
            item.setIsDefault("1");
            item.setGoodsId(goods.getGoods().getId());
            item.setSellerId(goods.getGoods().getSellerId());
            itemMapper.insertSelective(item);
        }
    }
	
	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
	    goods.getGoods().setAuditStatus("0");
        //初始化商品状态
        goods.getGoods().setAuditStatus("0");
        //修改商品对象
        goodsMapper.updateByPrimaryKey(goods.getGoods());
        //修改商品描述对象
        tbGoodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());

        //先删除商品数据
        //根据外键删除
        //创建example对象
        TbItemExample example = new TbItemExample();
        //创建criteria对象
        com.pyg.pojo.TbItemExample.Criteria createCriteria = example.createCriteria();
        //设置参数,根据外键删除
        createCriteria.andGoodsIdEqualTo(goods.getGoods().getId());

        //删除操作
        itemMapper.deleteByExample(example);

        saveItemList(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		Goods goods = new Goods();
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);
        // 查询商品列表
        TbItemExample itemExample = new TbItemExample();
        TbItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(itemExample);
        goods.setItemList(tbItems);
        return goods;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete("1");
            goodsMapper.updateByPrimaryKeySelective(tbGoods);
        }
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();
		if(goods!=null){			
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				//criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatusByIds(Long[] ids, String status) {
		for(Long id : ids){
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKeySelective(tbGoods);
        }
	}

}
