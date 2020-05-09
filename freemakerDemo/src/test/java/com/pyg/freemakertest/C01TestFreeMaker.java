package com.pyg.freemakertest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class C01TestFreeMaker {

    /**
     * 根据模版和数据生成html
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test1() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C01test.ftl");
        // 5、创建模型数据
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("message","欢迎使用freemaker");
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(map,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * assign指令
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test2() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C02Assigntest.ftl");

        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(null,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * include指令
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test3() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C04Include.ftl");

        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(null,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * ifelse指令
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test4() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C05Ifelse.ftl");
        // 5、创建模型数据
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(map,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * list指令
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test5() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C06list.ftl");
        // 5、创建模型数据
        Map<String,Object> map = new HashMap<>();
        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);
        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);
        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);

        map.put("goodsList", goodsList);
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(map,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * 内建函数
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test6() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C07build-infunction.ftl");
        // 5、创建模型数据
        Map<String,Object> map = new HashMap<>();
        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);
        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);
        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);

        map.put("goodsList", goodsList);
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(map,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * json字符串转化为对象
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test7() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C08JSONStrToObject.ftl");
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(null,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * 日期处理和格式化
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test8() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C09DateFormet.ftl");
        // 5、创建模型数据
        Map<String,Object> map = new HashMap<>();
        map.put("today",new Date());
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(map,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * 数字转化为字符串，可以去掉千分位逗号
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test9() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C10NumberToString.ftl");
        // 5、创建模型数据
        Map<String,Object> map = new HashMap<>();
        map.put("point",1029021236);
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(map,writer);
        // 8、关闭writer对象
        writer.close();
    }

    /**
     * 空字符串的处置
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test10() throws IOException, TemplateException {
        // 1、创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 2、设置模版所在的目录
        configuration.setDirectoryForTemplateLoading(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template"));
        // 3、设置模版字符集合
        configuration.setDefaultEncoding("UTF-8");
        // 4、加载模版
        Template template = configuration.getTemplate("C11NullDeal.ftl");
        // 6、创建writer对象并指定输出位置
        Writer writer = new FileWriter(new File("I:\\pyg_parent\\freemakerDemo\\src\\main\\resources\\template\\test.html"));
        // 7、输出
        template.process(null,writer);
        // 8、关闭writer对象
        writer.close();
    }
}
