package com.baizhi.zjj.dao;


import com.baizhi.zjj.cache.MyBatisCache;
import com.baizhi.zjj.entity.Banner;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
//@CacheNamespace(implementation = MyBatisCache.class)   //使用自己写的实现缓存
@CacheNamespace   //使用mybatis自带的二级缓存
public interface BannerDao extends Mapper<Banner>, InsertListMapper<Banner>, DeleteByIdListMapper<Banner,String> {
    List<Banner> queryBannersByTime();

}
