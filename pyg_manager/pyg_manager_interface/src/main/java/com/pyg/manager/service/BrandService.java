package com.pyg.manager.service;

import com.pyg.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    /**
     * 查询所有品牌数据
     * @return
     */
    List<TbBrand> findAll();
}
