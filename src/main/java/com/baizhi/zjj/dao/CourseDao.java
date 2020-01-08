package com.baizhi.zjj.dao;


import com.baizhi.zjj.entity.Album;
import com.baizhi.zjj.entity.Course;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CourseDao extends Mapper<Course>, InsertListMapper<Course>, DeleteByIdListMapper<Course,String> {
}
