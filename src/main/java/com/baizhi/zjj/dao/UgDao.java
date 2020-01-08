package com.baizhi.zjj.dao;



import com.baizhi.zjj.entity.Ug;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface UgDao extends Mapper<Ug>, InsertListMapper<Ug>, DeleteByIdListMapper<Ug,String> {
}
