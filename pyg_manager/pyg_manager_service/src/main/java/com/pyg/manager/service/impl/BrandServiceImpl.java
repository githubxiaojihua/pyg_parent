package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.BrandService;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbBrandExample;
import com.pyg.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

@Service//使用dubbo发布为服务，需要配置注解扫描
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public List<TbBrand> findAll() {
        List<TbBrand> tbBrands = tbBrandMapper.selectByExample(new TbBrandExample());
        return tbBrands;
    }

    /**
     * 使用分页插件查询数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<TbBrand> findPage(int pageNum, int pageSize) {
        // 初始化mybatis分页组件，此组件已经在SqlMapConfig.xml中配置了
        // 是一个监听器，也就是每次在查询mybatis的时候都会进行过滤分页
        // 此句必须在查询操作之前
        PageHelper.startPage(pageNum,pageSize);
        //List={page[total,pagesie],list}
        Page<TbBrand> page = (Page<TbBrand>)tbBrandMapper.selectByExample(new TbBrandExample());
        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * 注意使用insertSelective，因为id为自增长
     * 前台也没有传递
     * @param tbBrand
     */
    @Override
    public void addBrand(TbBrand tbBrand) {
        tbBrandMapper.insertSelective(tbBrand);
    }

    @Override
    public void update(TbBrand tbBrand) {
        tbBrandMapper.updateByPrimaryKey(tbBrand);
    }

    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }

    /**
     * 条件查询
     * @param tbBrand
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<TbBrand> findPage(TbBrand tbBrand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        TbBrandExample tbBrandExample = new TbBrandExample();
        TbBrandExample.Criteria criteria = tbBrandExample.createCriteria();
        if(tbBrand != null){
            if(!StringUtils.isEmpty(tbBrand.getName())){
                criteria.andNameLike("%" + tbBrand.getName() + "%");
            }
            if(!StringUtils.isEmpty(tbBrand.getFirstChar())){
                criteria.andFirstCharEqualTo(tbBrand.getFirstChar());
            }
        }
        Page<TbBrand> page = (Page<TbBrand>)tbBrandMapper.selectByExample(tbBrandExample);
        return new PageResult<>(page.getTotal(),page.getResult());
    }
}
