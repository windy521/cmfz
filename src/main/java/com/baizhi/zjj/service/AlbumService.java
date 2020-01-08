package com.baizhi.zjj.service;

import com.baizhi.zjj.entity.Album;

import java.util.List;
import java.util.Map;


public interface AlbumService {


    Album queryById(String id);

    public Map<String,Object> queryAllByPage(Integer page, Integer rows);

    Album insert(Album album);

    Album update(Album album);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}