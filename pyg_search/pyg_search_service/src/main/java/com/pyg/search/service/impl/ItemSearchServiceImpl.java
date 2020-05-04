package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(timeout = 1000000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate template;

    @Override
    public Map<String, Object> search(Map searchMap) {
        // 创建返回结果对象，封装结果
        Map<String,Object> result = new HashMap<>();
        // 创建query查询对象封装条件
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        Criteria criteria = null;
        // 设置高亮选项
        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style='color:red'>");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);
        // 封装查询条件
        // 1、主查询条件
        String keyWords = (String) searchMap.get("keywords");
        if(!StringUtils.isEmpty(keyWords)){
            criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        }else{
            criteria = new Criteria().expression("*:*");
        }
        query.addCriteria(criteria);

        // 2,根据分类查询
        // 获取分类参数
        String category = (String) searchMap.get("category");
        // 判断分类值是否存在
        if (category != null && !"".equals(category)) {
            // 根据分类查询
            Criteria criteria2 = new Criteria("item_category").is(category);
            // 创建过滤对象
            FilterQuery filterQuery = new SimpleFilterQuery(criteria2);
            // 把过滤查询添加query对象
            query.addFilterQuery(filterQuery);
        }

        // 3,根据品牌过滤查询
        // 获取品牌参数
        String brand = (String) searchMap.get("brand");
        // 判断分类值是否存在
        if (brand != null && !"".equals(brand)) {
            // 根据分类查询
            Criteria criteria2 = new Criteria("item_brand").is(brand);
            // 创建过滤对象
            FilterQuery filterQuery = new SimpleFilterQuery(criteria2);
            // 把过滤查询添加query对象
            query.addFilterQuery(filterQuery);
        }

        // 4,根据规格查询
        // 获取规格对象
        Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
        // 获取规格值
        for (String key : specMap.keySet()) {
            String value = (String) specMap.get(key);
            // 根据分类查询
            Criteria criteria2 = new Criteria("item_spec_" + key).is(value);
            // 创建过滤对象
            FilterQuery filterQuery = new SimpleFilterQuery(criteria2);
            // 把过滤查询添加query对象
            query.addFilterQuery(filterQuery);
        }

        // 6,价格过滤查询
        // 获取价格值
        // 数据格式:0-500,500-1000....
        String price = (String) searchMap.get("price");
        // 判断分类值是否存在
        if (price != null && !"".equals(price)) {

            // 切割价格
            String[] prices = price.split("-");

            // 判断最低价格如何不是0
            if (prices[0] != "0") {
                // 根据分类查询
                Criteria criteria2 = new Criteria("item_price")
                        .greaterThanEqual(prices[0]);
                // 创建过滤对象
                FilterQuery filterQuery = new SimpleFilterQuery(criteria2);
                // 把过滤查询添加query对象
                query.addFilterQuery(filterQuery);

            }

            // 判断最高价格不为*
            if (prices[1] != "*") {
                // 根据分类查询
                Criteria criteria2 = new Criteria("item_price")
                        .lessThanEqual(prices[1]);
                // 创建过滤对象
                FilterQuery filterQuery = new SimpleFilterQuery(criteria2);
                // 把过滤查询添加query对象
                query.addFilterQuery(filterQuery);
            }

        }

        // 7、设置排序
        String sortField = (String) searchMap.get("sortField");
        String sortValue = (String) searchMap.get("sort");
        if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortValue)){
            if("desc".equals(sortValue)){
                Sort sort = new Sort(Sort.Direction.DESC,"item_"+sortField);
                query.addSort(sort);
            }else{
                Sort sort = new Sort(Sort.Direction.ASC,"item_"+sortField);
                query.addSort(sort);
            }

        }else{
            Sort sort = new Sort(Sort.Direction.ASC,"item_price");
            query.addSort(sort);
        }

        //8,分页查询
        //获取分页值
        //获取当前页
        Integer pageNo = (Integer) searchMap.get("pageNo");
        //获取每页显示条数
        Integer pageSize = (Integer) searchMap.get("pageSize");

        //判断当前页是否为空
        if(pageNo==null){
            pageNo=1;
        }
        //判断每页显示的条数是否为空
        if(pageSize==null){
            pageSize=30;
        }

        //计算查询起始页
        Integer startNo = (pageNo-1)*pageSize;
        //设置分页
        query.setOffset(startNo);
        query.setRows(pageSize);

        // 执行查询
        HighlightPage<TbItem> pageItems = template.queryForHighlightPage(query, TbItem.class);
        // 设置高亮值
        for(HighlightEntry<TbItem> tbItem : pageItems.getHighlighted()){
            // 获取原始tbItem对象
            TbItem entity = tbItem.getEntity();
            if(tbItem.getHighlights().size()>0 && tbItem.getHighlights().get(0).getSnipplets().size()>0){
                entity.setTitle(tbItem.getHighlights().get(0).getSnipplets().get(0));
            }
        }
        // 封装结果
        result.put("rows",pageItems.getContent());

        //封装总页码
        result.put("totalPages", pageItems.getTotalPages());
        //封装总记录数
        result.put("total", pageItems.getTotalElements());
        return result;
    }
}
