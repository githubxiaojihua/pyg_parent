package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController//controller和responseBody的集合
@RequestMapping("/brand")
public class BrandController {

    // 使用dubbo注解注入远程服务对象
    @Reference(timeout=1000000)
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        List<TbBrand> list = brandService.findAll();
        return list;
    }
}
