package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.HashMap;
import java.util.Map;

@Service(timeout = 1000000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate template;

    @Override
    public Map<String, Object> search(Map searchMap) {
        Map<String,Object> result = new HashMap<>();
        SimpleHighlightQuery query = new SimpleHighlightQuery();
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        HighlightPage<TbItem> tbItems = template.queryForHighlightPage(query, TbItem.class);
        result.put("rows",tbItems.getContent());
        return result;
    }
}
