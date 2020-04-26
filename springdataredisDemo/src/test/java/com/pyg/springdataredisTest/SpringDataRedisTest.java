package com.pyg.springdataredisTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class SpringDataRedisTest {

    @Autowired
    private RedisTemplate template;

    /**
     * 设置value类型的数据
     */
    @Test
    public void testValue(){
        template.boundValueOps("name").set("张三");
        String name = (String) template.boundValueOps("name").get();
        System.out.println(name);
        template.delete("name");
        name = (String) template.boundValueOps("name").get();
        System.out.println(name);
    }


    /**
     * 设置Set类型的数据
     */
    @Test
    public void testSet(){
        // 设置值
        template.boundSetOps("nameSet").add("曹操");
        template.boundSetOps("nameSet").add("刘备");
        template.boundSetOps("nameSet").add("孙权");

        // 获取值
        Set nameSet = template.boundSetOps("nameSet").members();
        System.out.println(nameSet);

        // 删除集合中的某个值
        template.boundSetOps("nameSet").remove("刘备");
        nameSet = template.boundSetOps("nameSet").members();
        System.out.println(nameSet);

        // 删除整个set
        template.delete("nameSet");

    }

    /**
     * 设置list
     */
    @Test
    public void testList(){
        template.delete(Arrays.asList("nameList1","nameList2"));
        // 右压栈
        template.boundListOps("nameList1").rightPush("刘备");
        template.boundListOps("nameList1").rightPush("关羽");
        template.boundListOps("nameList1").rightPush("张飞");

        List nameList1 = template.boundListOps("nameList1").range(0, 10);
        System.out.println(nameList1);

        // 左压栈
        template.boundListOps("nameList2").leftPush("刘备");
        template.boundListOps("nameList2").leftPush("关羽");
        template.boundListOps("nameList2").leftPush("张飞");
        List nameList2 = template.boundListOps("nameList2").range(0, 10);
        System.out.println(nameList2);

        // 根据索引查询数据
        String name01 = (String) template.boundListOps("nameList1").index(1);
        System.out.println(name01);

        // 删除某个元素1代表count，如果有多个关羽则只删除一个
        template.boundListOps("nameList1").remove(1,"关羽");
        nameList1 = template.boundListOps("nameList1").range(0, 10);
        System.out.println(nameList1);
    }

    /**
     * 测试hash
     */
    @Test
    public void hashTest(){

        // 设置hash值
        template.boundHashOps("nameHash").put("a","唐僧");
        template.boundHashOps("nameHash").put("b","悟空");
        template.boundHashOps("nameHash").put("c","八戒");
        template.boundHashOps("nameHash").put("d","沙僧");

        // 获取所有的key
        Set nameHashKeys = template.boundHashOps("nameHash").keys();
        System.out.println(nameHashKeys);

        // 获取所有的值
        List nameHashValue = template.boundHashOps("nameHash").values();
        System.out.println(nameHashValue);

        // 根据key提取值
        Object o = template.boundHashOps("nameHash").get("a");
        System.out.println(o);

        // 根据key移除值
        template.boundHashOps("nameHash").delete("c");

    }
}
