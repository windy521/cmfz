package com.baizhi.zjj.dao;


import com.baizhi.zjj.entity.Album;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface AlbumDao extends Mapper<Album>, InsertListMapper<Album>, DeleteByIdListMapper<Album,String> {
}
