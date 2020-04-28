package com.pyg.solrutil;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private SolrTemplate template;

    public void importItemData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");

        System.out.println("====导入商品列表====");
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        for(TbItem item : tbItems){
            //导入动态域，关于动态域的说明可以看笔记中的说明：对于solr动态域的一些理解
            Map<String,String> specMap = JSON.parseObject(item.getSpec(),Map.class);
            item.setSpecMap(specMap);
            System.out.println(item.getTitle());
        }
        template.saveBeans(tbItems);
        template.commit();
        System.out.println("====结束====");
    }

    // 可以直接使用 java -jar **.jar来进行数据导入
    public static void main(String[] args){
        // 注意此处必须为classpath*
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();
    }
}
