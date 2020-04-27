package com.springdatasolr.test;

import com.springdatasolrdemo.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext-solr.xml")
public class SpringDataSolrTest {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 增加
     */
    @Test
    public void testAdd(){
        TbItem item = new TbItem();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为 2号专卖店");
        item.setTitle("华为 Mate9");
        item.setPrice(new BigDecimal(2000));

        // 添加数据
        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    /**
     * 按照Id查找
     */
    @Test
    public void testFindById(){
        TbItem item = solrTemplate.getById(1, TbItem.class);
        System.out.println(item.getTitle());
    }

    /**
     * 按照id删除
     */
    @Test
    public void testDeleById(){
        solrTemplate.deleteById("1");
        solrTemplate.commit();
    }

    /**
     * 测试增加list
     */
    @Test
    public void testAddList(){
        List<TbItem> items = new ArrayList<>();
        for(int i=0;i<100;i++){
            TbItem item=new TbItem();
            item.setId(i+1L);
            item.setBrand("华为");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为 2号专卖店");
            item.setTitle("华为 Mate"+i);
            item.setPrice(new BigDecimal(2000+i));
            items.add(item);
        }
        solrTemplate.saveBeans(items);
        solrTemplate.commit();
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testPageQuery(){
        Query query = new SimpleQuery("*:*");
        query.setOffset(20);//设置查询起始位置
        query.setRows(20);//查询每页记录数

        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数：" + tbItems.getTotalElements());

        List<TbItem> content = tbItems.getContent();
        showList(content);

    }

    /**
     * 测试条件查询
     */
    @Test
    public void testPageQueryMutil(){
        Query query=new SimpleQuery("*:*");
        Criteria criteria=new Criteria("item_title").contains("2");
        criteria=criteria.and("item_title").contains("5");
        query.addCriteria(criteria);
        //query.setOffset(20);//开始索引（默认 0）
        //query.setRows(20);//每页记录数(默认 10)
        ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
        System.out.println("总记录数："+page.getTotalElements());
        List<TbItem> list = page.getContent();
        showList(list);
    }

    /**
     * 测试删除所有
     */
    @Test
    public void testDeleteAll(){
        Query query=new SimpleQuery("*:*");
        solrTemplate.delete(query);
        solrTemplate.commit();
    }

    private void showList(List<TbItem> list){
        for(TbItem item:list){
            System.out.println(item.getTitle() +item.getPrice());
        }
    }



}
