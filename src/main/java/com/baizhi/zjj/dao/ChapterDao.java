package com.baizhi.zjj.dao;



import com.baizhi.zjj.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ChapterDao extends Mapper<Chapter>, InsertListMapper<Chapter>, DeleteByIdListMapper<Chapter,String> {
//    List<Chapter> selectAllByAlbumId(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("id") String id);
  //  Integer selectCountByAlbumId(String id);
}
