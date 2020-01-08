package com.baizhi.zjj.service;

import com.baizhi.zjj.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User queryById(String id);

    Map queryAll();

    Map queryAllByPage(Integer page, Integer rows);

    User insert(User user);

    User update(User user);

    boolean deleteById(String id);

    int deleteByIdList(List<String> list);

}