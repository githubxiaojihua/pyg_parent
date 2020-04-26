package com.pyg.manager.controller;

import com.pyg.utils.FastDFSClient;
import com.pyg.utils.PygResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;// 文件服务器地址

    @RequestMapping("/upload")
    public PygResult upload(MultipartFile file){
        // 1、获取文件扩展名称
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        FastDFSClient client = null;
        try {
            // 2、上传文件
            client = new FastDFSClient("classpath:config/client.conf");
            String path = client.uploadFile(file.getBytes(), extName);
            // 3、拼接Path
            path = FILE_SERVER_URL + path;
            // 4、返回结果
            return new PygResult(true,path);

        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false,"上传失败");
        }
    }
}
