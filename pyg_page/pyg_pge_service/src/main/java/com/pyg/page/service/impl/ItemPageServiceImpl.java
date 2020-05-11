package com.pyg.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.page.service.ItemPageService;
import com.pyg.pojo.TbGoods;
import com.pyg.pojo.TbGoodsDesc;
import com.pyg.pojo.TbGoodsDescExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 10000000)
public class ItemPageServiceImpl implements ItemPageService {

    @Value("${pagedir}")
    private String pageDir;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;



    @Override
    public boolean getItemHtml(Long goodsId){

        try {
            // 1、获取模版
            Configuration configuration = freeMarkerConfig.getConfiguration();
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate("item.ftl");
            // 2、准备数据
            Map<String,Object> modelMap = new HashMap<>();
            // 2.1 查询goods数据
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(goodsId);
            modelMap.put("goods",tbGoods);
            // 2.2 查询商品描述数据
            TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            modelMap.put("goodsDesc",tbGoodsDesc);
            // 3、生成html
            // 由于读取模版的时候配置的编码格式为utf-8，但是按照教程中写的时候writer的默认编码是gbk
            // 那么就需要使用outputstreamwriter进行字节和字符之间的转换，并指定编码格式
            // 先使用字节流读取，然后使用outputstreamwriter进行字节和字符流进行转化
            //Writer out = new FileWriter(pageDir + goodsId + ".html");
            FileOutputStream fos = new FileOutputStream(pageDir + goodsId + ".html");
            OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
            //String end = ((FileWriter) osw).getEncoding();
            template.process(modelMap,osw);
            // 4、关闭资源
            osw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
