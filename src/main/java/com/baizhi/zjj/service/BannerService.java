package com.baizhi.zjj.service;

import com.baizhi.zjj.entity.Banner;

import java.util.List;
import java.util.Map;


public interface BannerService {


    Banner queryById(String id);

    public Map<String,Object> queryAllByPage(Integer page, Integer rows);

    Banner insert(Banner banner);

    Banner update(Banner banner);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}