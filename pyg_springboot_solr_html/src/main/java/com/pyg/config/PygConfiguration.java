package com.pyg.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * 自定义配置文件来定义SolrTemplate因为SolrTemplate没有在springboot中自动注入
 * 其实通过跟踪SolrAutoConfiguration.class源码可以知道，只要spring.data.solr前缀的话就会
 * 自动初始化HttpSolrClient
 */
@Configuration
public class PygConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private SolrClient solrClient;

    @Bean
    public SolrTemplate getSolrTemplate(){
        //SolrClient solrClient = new HttpSolrClient(env.getProperty("spring.data.solr.host"));
        SolrTemplate solrTemplate = new SolrTemplate(solrClient);
        return solrTemplate;
    }
}
