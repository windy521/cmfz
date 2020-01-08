package com.baizhi.zjj.controller;

import com.baizhi.zjj.dao.ArticleDao;
import com.baizhi.zjj.entity.Article;
import com.baizhi.zjj.service.ArticleService;
import com.baizhi.zjj.util.HttpUtil;
import com.baizhi.zjj.util.UploadUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleDao articleDao;

    @RequestMapping("selectOne")
    public Article selectOne(String id) {
        return this.articleService.queryById(id);
    }

    @RequestMapping("queryAll")
    public Map queryAll(){
        System.out.println("article----queryAll");
        Map map = articleService.queryAll();
        return map;
    }

    @RequestMapping("queryAllByPage")
    public Map queryAllByPage(Integer page, Integer rows){
        System.out.println("article----queryAllByPage");
        Map map = articleService.queryAllByPage(page, rows);
        return map;
    }

    @RequestMapping("deleteById")
    public void deleteById(String id){
        System.out.println("article----deleteById");
        boolean b = articleService.deleteById(id);
        System.out.println("删除结果 = " + b);
    }



    @RequestMapping("edit")
     // 返回值是void，相当于把空字符 响应ajax回client
    // 参数oper，代表增删改的具体操作类型，名称 固定（jqgrid控制封装好的参数）
    public Map<String, Object> edit(Article article, String oper, String[ ] id){
        Map<String, Object> map = new HashMap<>();
        System.out.println("...执行增删改操作...edit...");
        if ("add".equals(oper)){
            System.out.println("添加操作。。。执行");
            Article b = articleService.insert(article);
            map.put("articleId",b.getId());
        } else if ("edit".equals(oper)){
            System.out.println("修改操作。。。执行");
            articleService.update(article);
            //map.put("articleId",article.getId());
        } else {
            System.out.println("删除操作(可批量删除)。。。执行");
            //articleService.deleteById(article.getId());
            int i = articleService.deleteByIdList(Arrays.asList(id));
            System.out.println("删除了"+ i + "条数据");
        }
        return map;
    }
    //文件上传
    @RequestMapping("uploadArticle")
    public void uploadArticle(MultipartFile photo, String articleId, HttpSession session) {
        System.out.println("uploadArticle：文件上传");
        System.out.println("MultipartFile："+ photo);
        // 获取真实路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
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
        // new Article(); 设置id(找到原始数据) url(更新使用)
        // articleDao.updateByPrimaryKeySetive(article);
        if(originalFilename.equals("")){
            Article article = new Article();
            article.setId(articleId);
            article.setImg("/upload/articleImg/"+"默认封面.jpg");
            articleService.update(article);
        }else {
            Article article = new Article();
            article.setId(articleId);
            article.setImg("/upload/articleImg/"+newFileName);
            articleService.update(article);
        }
    }
    //---------------------------------------------------------------------------------------

    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile, HttpSession session, HttpServletRequest request){
        // 该方法需要返回的信息 error 状态码 0 成功 1失败   成功时 url 图片路径
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        if (!file.exists()){
            file.mkdirs();
        }
        // 路径
        try{
            //String uploadPath = UploadUtil.getUploadPath(imgFile, request, "/upload/articleImg/");
            String http = HttpUtil.getHttp(imgFile, request, "/upload/articleImg/");
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }
    @RequestMapping("showAllImg")
    public Map showAllImg(HttpServletRequest request, HttpSession session){
        HashMap hashMap = new HashMap();
        hashMap.put("current_url",request.getContextPath()+"/upload/articleImg/");
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            // 通过字符串拆分获取时间戳
            String time = name.split("_")[0];
            // 创建SimpleDateFormat对象 指定yyyy-MM-dd hh:mm:ss 样式
            //  simpleDateFormat.format() 获取指定样式的字符串(yyyy-MM-dd hh:mm:ss)
            // format(参数)  参数:时间类型   new Date(long类型指定时间)long类型  现有数据:字符串类型时间戳
            // 需要将String类型 转换为Long类型 Long.valueOf(str);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datetime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }
    @RequestMapping("insertArticle")
    public String insertArticle(Article article,MultipartFile inputfile,HttpServletRequest request){
        //if (article.getId().equals("")){
        if (article.getId()==null||"".equals(article.getId())){
            //文件上传
            String uploadPath = UploadUtil.getUploadPath(inputfile, request, "/upload/articleImg/");
            System.out.println("uploadPath = " + uploadPath);

            String originalFilename = inputfile.getOriginalFilename();
            if(originalFilename.equals("")){
                article.setImg("/upload/articleImg/"+"默认封面.jpg");
            }else {
                article.setImg(uploadPath);
            }

            // insert
            System.out.println("文章添加");
            articleService.insert(article);


        }else{
            // update
            System.out.println("文章修改");
            articleService.update(article);
        }
        System.out.println(article);
        System.out.println(inputfile);
        return "ok";
    }

    //---------------------------------------------接口文档----------------------------------------
/*    //6. 文章详情接口
    @RequestMapping("queryArticleDetail")
    public Map queryArticleDetail(String uid,String id){
        HashMap map = new HashMap<>();
        Article article = articleDao.selectByPrimaryKey(id);
        map.put("article",article);
        map.put("status","200");
        return map;
    }*/


}