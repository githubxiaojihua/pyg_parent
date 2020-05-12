package com.pyg.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.mapper.TbGoodsDescMapper;
import com.pyg.mapper.TbGoodsMapper;
import com.pyg.mapper.TbItemCatMapper;
import com.pyg.mapper.TbItemMapper;
import com.pyg.page.service.ItemPageService;
import com.pyg.pojo.*;
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

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;



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

            // 2.3 商品分类
            String itemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
            modelMap.put("itemCat1", itemCat1);
            modelMap.put("itemCat2", itemCat2);
            modelMap.put("itemCat3", itemCat3);

            //4.SKU 列表
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            // 可选择id为149187842867957的商品
            //criteria.andStatusEqualTo("1");//状态为有效，此处需要修改一下数据库因为以前的逻辑有问题所以有写地方还是为0
            criteria.andGoodsIdEqualTo(goodsId);//指定 SPU ID
            example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认
            List<TbItem> itemList = itemMapper.selectByExample(example);
            modelMap.put("itemList", itemList);

            // 5、生成html
            // 由于读取模版的时候配置的编码格式为utf-8，但是按照教程中写的时候writer的默认编码是gbk
            // 那么就需要使用outputstreamwriter进行字节和字符之间的转换，并指定编码格式
            // 先使用字节流读取，然后使用outputstreamwriter进行字节和字符流进行转化
            //Writer out = new FileWriter(pageDir + goodsId + ".html");
            FileOutputStream fos = new FileOutputStream(pageDir + goodsId + ".html");
            OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");
            //String end = ((FileWriter) osw).getEncoding();
            template.process(modelMap,osw);
            // 6、关闭资源
            osw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
