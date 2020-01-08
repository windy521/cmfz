package com.baizhi.zjj.dao;

import com.baizhi.zjj.entity.Admin;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin>, InsertListMapper<Admin>, DeleteByIdListMapper<Admin,String> {
}
