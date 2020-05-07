package com.pyg.utils;

import com.alibaba.fastjson.JSON;
import com.pyg.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 构建更新solr缓存以及静态化html页面的服务
 */
@Component
public class SolrHtmlUtils {

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 根据接收到的消息类型，来更新solr或者是静态化页面
     * @param jsonStr
     */
    @JmsListener(destination="add_update_del_topic")
    public void addIndexGenHtml(String jsonStr){
        //判断传递的值是否为空
        if(StringUtils.isNotBlank(jsonStr) && jsonStr.contains("add_update")){
            //截取
            String[] str = jsonStr.split("=");
            //转换
            List<TbItem> list = JSON.parseArray(str[1], TbItem.class);
            // 获取map接口key
            // 判断操作是添加,修改
            // 添加修改
            // 获取添加消息
            //List<TbItem> list = (List<TbItem>) messageMap.get("add_update");
            // 判断获取消息是否为空
            if (list!=null && list.size()>0) {
                // 把字符串转换成对象
                //List<TbItem> list = JSON.parseArray(str, TbItem.class);
                //同步索引库
                solrTemplate.saveBeans(list);
                // 提交
                solrTemplate.commit();
            }

        } else {
            // 获取id
            //String ids = (String) messageMap.get("dele");
            //把ids转换成集合
            List<String> list = JSON.parseArray(jsonStr, String.class);
            // 删除
            solrTemplate.deleteById(list);
            // 提交
            solrTemplate.commit();
        }

    }

}
