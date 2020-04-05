package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.manager.service.BrandService;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.pojo.TbBrandExample;
import org.springframework.beans.factory.annotation.Autowired;

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
}
