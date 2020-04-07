package com.pyg.manager.service;

import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;

import java.util.List;

public interface BrandService {
    /**
     * 查询所有品牌数据
     * @return
     */
    List<TbBrand> findAll();

    /**
     * 分页查询品牌数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<TbBrand> findPage(int pageNum,int pageSize);

    /**
     * 增加品牌
     * @param tbBrand
     */
    void addBrand(TbBrand tbBrand);

    /**
     * 更新
     * @param tbBrand
     */
    void update(TbBrand tbBrand);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    TbBrand findOne(Long id);

    /**
     * 按照条件查询
     * @param tbBrand
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<TbBrand> findPage(TbBrand tbBrand,int pageNum,int pageSize);

    void delete(Long[] ids);
}
