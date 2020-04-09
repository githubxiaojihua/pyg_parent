package com.pyg.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    /**
     * 分页查询，接收来自前端分页插件的page和rows参数
     * @param page 当前页
     * @param rows 每页记录数
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult<TbBrand> findPage(int page, int rows){
        return brandService.findPage(page,rows);
    }

    /**
     * 使用RequestBody来接收前台传过来的json字符串，并转换为对象
     * @param tbBrand
     * @return
     */
    @RequestMapping("/add")
    public PygResult add(@RequestBody TbBrand tbBrand){

        try{
            brandService.addBrand(tbBrand);
            return new PygResult(true,"增加成功");
        }catch(Exception e){
            return new PygResult(false,"增加失败");
        }

    }

    @RequestMapping("/update")
    public PygResult update(@RequestBody TbBrand tbBrand){
        try{
            brandService.update(tbBrand);
            return new PygResult(true,"修改成功");
        }catch(Exception e){
            return new PygResult(false,"修改失败");
        }
    }

    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
    }

    @RequestMapping("/search")
    public PageResult<TbBrand> search(@RequestBody TbBrand tbBrand,int page,int rows){
        return brandService.findPage(tbBrand,page,rows);
    }

    @RequestMapping("/delete")
    public PygResult delete(Long[] ids){
        try{
            brandService.delete(ids);
            return new PygResult(true,"删除成功");
        }catch(Exception e){
            e.printStackTrace();
            return new PygResult(false,"删除失败");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }



}
