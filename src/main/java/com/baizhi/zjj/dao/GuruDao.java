package com.baizhi.zjj.dao;


import com.baizhi.zjj.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface GuruDao extends Mapper<Guru>, InsertListMapper<Guru>, DeleteByIdListMapper<Guru,String> {
}
