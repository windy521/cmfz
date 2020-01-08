package com.baizhi.zjj.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class UploadUtil {
    public static String getUploadPath(MultipartFile multipartFile,HttpServletRequest request,String path){
        System.out.println("文件上传执行");
        System.out.println("MultipartFile："+ multipartFile);
        String realPath = request.getSession().getServletContext().getRealPath(path);
        // 判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            // mkdirs() 多级创建
            file.mkdirs();
        }
        // 防止文件重名
        String originalFilename = multipartFile.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;


        try {
            multipartFile.transferTo(new File(realPath,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }


        String uploadPath = path + newFileName;

        return uploadPath;
    }
}
