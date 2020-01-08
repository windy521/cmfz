package com.baizhi.zjj.service;

import com.baizhi.zjj.entity.Guru;
import java.util.List;
import java.util.Map;

public interface GuruService {

    Guru queryById(String id);

    Map queryAllMap();

    List<Guru> queryAll();

    Map queryAllByPage(Integer page, Integer rows);

    Guru insert(Guru guru);

    Guru update(Guru guru);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}