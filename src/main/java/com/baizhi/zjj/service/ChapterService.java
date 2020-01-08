package com.baizhi.zjj.service;

import com.baizhi.zjj.entity.Chapter;

import java.util.List;
import java.util.Map;


public interface ChapterService {


    Chapter queryById(String id);

    public Map<String,Object> queryAllByPage(Integer page, Integer rows,String id);

    Chapter insert(Chapter chapter);

    Chapter update(Chapter chapter);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}