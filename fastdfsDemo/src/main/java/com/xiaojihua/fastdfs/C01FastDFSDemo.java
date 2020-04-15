package com.xiaojihua.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

public class C01FastDFSDemo {

    /**
     * 测试原生API
     * @throws Exception
     */
    @Test
    public void testFastDFS() throws Exception {
        // 1、加载配置文件，配置文件的内容就是tracker服务的地址
        ClientGlobal.init("I:\\pyg_parent\\fastdfsDemo\\src\\main\\resources\\client.conf");
        // 2、创建一个TrackerClient对象
        TrackerClient client = new TrackerClient();
        // 3、使用TrackerClient创建链接，获得一个TrackerServer对象
        TrackerServer trackerServer = client.getConnection();
        // 4、创建一个StorageServer的引用，值为null
        StorageServer storageServer = null;
        // 5、创建一个StorageClient对象，需要两个参数分别是TrackerServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        // 6、使用StorageClient对象上传图片
        String[] strings = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\123.jpg", "jpg", null);
        // 7、返回数组。包含组名和图片的路径
        for(String str : strings){
            /**
             * group1
             * M00/00/00/wKgZhV6WgEmAemwJAAA0F3lKMX8018.jpg
             * 要想访问图片则需要
             * http://192.168.25.133/group1/M00/00/00/wKgZhV6WgEmAemwJAAA0F3lKMX8018.jpg
             * 将两个路径组装起来
             */
            System.out.println(str);
        }
    }

    /**
     * 测试自定义工具类的使用
     */
    @Test
    public void testfASTDFSDemo() throws Exception {
        //指定客户端配置文件绝对路径
        String conf = "I:\\pyg_parent\\fastdfsDemo\\src\\main\\resources\\client.conf";
        //指定上传的图片地址
        String pic = "C:\\Users\\Administrator\\Pictures\\1.png";
        //使用工具类进行上传
        //创建工具类对象
        FastDFSClient fds = new FastDFSClient(conf);
        //上传
        String url = fds.uploadFile(pic);

        System.out.println(url);
    }
}
