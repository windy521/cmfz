package com.baizhi.zjj.controller;


import com.baizhi.zjj.entity.Album;
import com.baizhi.zjj.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("queryAllByPage")
    @ResponseBody
    public Map<String,Object> queryAllByPage(Integer page, Integer rows){
        Map<String, Object> map = albumService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody  // 返回值是void，相当于把空字符 响应ajax回client
    // 参数oper，代表增删改的具体操作类型，名称 固定（jqgrid控制封装好的参数）
    public Map<String, Object> edit(Album album, String oper,String[ ] id){
        Map<String, Object> map = new HashMap<>();
        System.out.println("...执行增删改操作...edit...");
        if ("add".equals(oper)){
            System.out.println("添加操作。。。执行");
            Album b = albumService.insert(album);
            map.put("albumId",b.getId());
        } else if ("edit".equals(oper)){
            System.out.println("修改操作。。。执行");
            albumService.update(album);
            //map.put("albumId",album.getId());
        } else {
            System.out.println("删除操作(可批量删除)。。。执行");
            //albumService.deleteById(album.getId());
            int i = albumService.deleteByIdList(Arrays.asList(id));
            System.out.println("删除了"+ i + "条数据");
        }
        return map;
    }

    @RequestMapping("uploadAlbum")
    public void uploadAlbum(MultipartFile cover, String albumId, HttpSession session) {
        System.out.println("MultipartFile："+ cover);
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            // mkdirs() 多级创建
            file.mkdirs();
        }
        // 防止文件重名
        String originalFilename = cover.getOriginalFilename();
        System.out.println("originalFilename = " + originalFilename);
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            cover.transferTo(new File(realPath, newFileName));
            // new Album(); 设置id(找到原始数据) url(更新使用)
            // albumDao.updateByPrimaryKeySetive(album);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(originalFilename.equals("")){
            Album album = new Album();
            album.setId(albumId);
            album.setCover("/upload/img/"+"默认封面.jpg");
            albumService.update(album);
        }else {
            Album album = new Album();
            album.setId(albumId);
            album.setCover("/upload/img/"+newFileName);
            albumService.update(album);
        }

    }


}
