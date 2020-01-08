package com.baizhi.zjj.controller;

import com.baizhi.zjj.dao.*;

import com.baizhi.zjj.entity.*;

import com.baizhi.zjj.dao.ArticleDao;
import com.baizhi.zjj.dao.BannerDao;
import com.baizhi.zjj.entity.Album;
import com.baizhi.zjj.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//5. 一级页面展示接口
@RequestMapping("onePage")
// 需要返回json格式的数据
@RestController
public class OnePageController {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleDao articleDao;
    /*@RequestMapping("onePage")
    // type : all|wen|si
    public Map onePage(String uid,String type,String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerDao.queryBannersByTime();
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                List<Article> articles = articleDao.selectAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(0, 5));
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleDao.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }

        return hashMap;
    }*/
}
