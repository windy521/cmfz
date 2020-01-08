package com.baizhi.zjj.controller;


import com.alibaba.excel.EasyExcel;
import com.baizhi.zjj.dao.BannerDao;
import com.baizhi.zjj.entity.Banner;
import com.baizhi.zjj.entity.Banner;
import com.baizhi.zjj.entity.BannerListener;
import com.baizhi.zjj.service.BannerService;
import com.baizhi.zjj.util.HttpUtil;
import com.baizhi.zjj.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private BannerDao bannerDao;

    @RequestMapping("queryAllByPage")
    @ResponseBody
    public Map<String,Object> queryAllByPage(Integer page, Integer rows){
        Map<String, Object> map = bannerService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    @ResponseBody  // 返回值是void，相当于把空字符 响应ajax回client
    // 参数oper，代表增删改的具体操作类型，名称 固定（jqgrid控制封装好的参数）
    public Map<String, Object> edit(Banner banner, String oper,String[ ] id){
        Map<String, Object> map = new HashMap<>();
        System.out.println("...执行增删改操作...edit...");
        if ("add".equals(oper)){
            System.out.println("添加操作。。。执行");
            Banner b = bannerService.insert(banner);
            map.put("bannerId",b.getId());
        } else if ("edit".equals(oper)){
            System.out.println("修改操作。。。执行");
            bannerService.update(banner);
            //map.put("bannerId",banner.getId());
        } else {
            System.out.println("删除操作(可批量删除)。。。执行");
            //bannerService.deleteById(banner.getId());
            int i = bannerService.deleteByIdList(Arrays.asList(id));
            System.out.println("删除了"+ i + "条数据");
        }
        return map;
    }

    @RequestMapping("uploadBanner")
    public void uploadBanner(MultipartFile url, String bannerId, HttpSession session) {

        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        // 判断该文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            // mkdirs() 多级创建
            file.mkdirs();
        }
        // 防止文件重名
        String originalFilename = url.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            url.transferTo(new File(realPath, newFileName));
            // new Banner(); 设置id(找到原始数据) url(更新使用)
            // bannerDao.updateByPrimaryKeySetive(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(originalFilename.equals("")){
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl("/upload/img/"+"默认封面.jpg");
            bannerService.update(banner);
        }else {
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl("/upload/img/"+newFileName);
            bannerService.update(banner);
        }
    }
//----------------------EasyExcel：使用EasyExcel完成导入导出Excel---------------------------------------------------

    //导出信息，生成Excel（下载文件）
    @RequestMapping("exportBanner")
    @ResponseBody
    public void exportBanner(HttpServletResponse response) throws IOException {
        List<Banner> bannerList = bannerDao.selectAll();
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("Banner信息"+new Date().getTime(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Banner.class).sheet("Banner模板").doWrite(bannerList);
    }

    //上传 + 从Excel导入信息
    @RequestMapping("importBanner")
    @ResponseBody
    public String importBanner(MultipartFile bannerFile, HttpServletRequest request) throws IOException {
        System.out.println("importBanner:上传 + 从Excel导入信息");
        //上传
        String uploadPath = UploadUtil.getUploadPath(bannerFile, request, "/upload/bannerExcel/");
       // String httpPath = HttpUtil.getHttp(bannerFile, request, "/upload/bannerExcel/");
        //String uploadPath = UploadUtil.getUploadPath(bannerFile, request, "\\upload\\bannerExcel\\");
        System.out.println("uploadPath = " + uploadPath);
        //String httpPath = HttpUtil.getHttp(bannerFile, request, "\\upload\\bannerExcel\\");
        String realPath = request.getServletContext().getRealPath(uploadPath);

        System.out.println("realPath = " + realPath);

        EasyExcel.read(realPath, Banner.class, new BannerListener(bannerService)).sheet().doRead();
        return "sss";
    }

    //测试 导出Excel(项目中没使用)
    @RequestMapping("outBanner")
    @ResponseBody
    public void outBanner(){
        List<Banner> bannerList = bannerDao.selectAll();
        //String fileName = "D:\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+".xlsx";
        String fileName = "E:\\轮播图"+new Date().getTime()+".xlsx";
        // write() 参数1:文件路径 参数2:实体类.class sheet()指定写入工作簿的名称 doWrite(List数据) 写入操作
        // 如需下载使用 参数1:outputSteam 参数2:实体类.class
        EasyExcel.write(fileName, Banner.class) // 指定文件导出的路径及样式
                .sheet("轮播图")           // 指定导出到哪个sheet工作簿
                //.doWrite(Arrays.asList(new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx"),new DemoData(UUID.randomUUID().toString(),18,new Date(),"Rxx")));
                .doWrite(bannerList);
        // 导出操作 准备数据
    }
//-------------------------------------------------------------------------------------------------------------------
}
