package com.baizhi.zjj.dao;


import com.baizhi.zjj.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner>, InsertListMapper<Banner>, DeleteByIdListMapper<Banner,String> {
    List<Banner> queryBannersByTime();

}
