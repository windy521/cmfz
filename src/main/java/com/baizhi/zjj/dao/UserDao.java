package com.baizhi.zjj.dao;


import com.baizhi.zjj.DTO.UserDTO;
import com.baizhi.zjj.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, InsertListMapper<User>, DeleteByIdListMapper<User,String> {
    Integer queryUserByTime(@Param("sex") String sex, @Param("day") Integer day);
    List<UserDTO> queryUserBySexLocation(String sex);
    List<User> queryUserRandom();

}
