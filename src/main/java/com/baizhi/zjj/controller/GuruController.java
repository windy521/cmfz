package com.baizhi.zjj.controller;

import com.baizhi.zjj.entity.Guru;
import com.baizhi.zjj.service.GuruService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("guru")
public class GuruController {

    @Resource
    private GuruService guruService;

    @RequestMapping("selectOne")
    public Guru selectOne(String id) {
        return this.guruService.queryById(id);
    }

    @RequestMapping("queryAllMap")
    public Map queryAllMap(){
        System.out.println("guru----queryAllMap");
        Map map = guruService.queryAllMap();
        return map;
    }
    @RequestMapping("queryAllList")
    public List<Guru> queryAll(){
        System.out.println("guru----queryAllList");
        List<Guru> guruList = guruService.queryAll();
        return guruList;
    }

    @RequestMapping("queryAllByPage")

    public Map queryAllByPage(Integer page, Integer rows){
        System.out.println("guru----queryAllByPage");
        Map map = guruService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
     // 返回值是void，相当于把空字符 响应ajax回client
    // 参数oper，代表增删改的具体操作类型，名称 固定（jqgrid控制封装好的参数）
    public Map<String, Object> edit(Guru guru, String oper, String[ ] id){
        Map<String, Object> map = new HashMap<>();
        System.out.println("...执行增删改操作...edit...");
        if ("add".equals(oper)){
            System.out.println("添加操作。。。执行");
            Guru b = guruService.insert(guru);
            map.put("guruId",b.getId());
        } else if ("edit".equals(oper)){
            System.out.println("修改操作。。。执行");
            guruService.update(guru);
            //map.put("guruId",guru.getId());
        } else {
            System.out.println("删除操作(可批量删除)。。。执行");
            //guruService.deleteById(guru.getId());
            int i = guruService.deleteByIdList(Arrays.asList(id));
            System.out.println("删除了"+ i + "条数据");
        }
        return map;
    }
    //文件上传
    @RequestMapping("uploadGuru")
    public void uploadGuru(MultipartFile photo, String guruId, HttpSession session) {
        System.out.println("uploadGuru：文件上传");
        System.out.println("MultipartFile："+ photo);
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            // mkdirs() 多级创建
            file.mkdirs();
        }
        // 防止文件重名
        String originalFilename = photo.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            photo.transferTo(new File(realPath, newFileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        // new Guru(); 设置id(找到原始数据) url(更新使用)
        // guruDao.updateByPrimaryKeySetive(guru);
        if(originalFilename.equals("")){
            Guru guru = new Guru();
            guru.setId(guruId);
            guru.setPhoto("/upload/img/"+"默认头像.jpg");
            guruService.update(guru);
        }else {
            Guru guru = new Guru();
            guru.setId(guruId);
            guru.setPhoto("/upload/img/"+newFileName);
            guruService.update(guru);
        }
    }

}