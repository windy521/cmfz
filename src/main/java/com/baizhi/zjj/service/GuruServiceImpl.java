package com.baizhi.zjj.service;

import com.baizhi.zjj.entity.Guru;
import com.baizhi.zjj.dao.GuruDao;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;
    @Override
    public Guru queryById(String id) {
        return this.guruDao.selectByPrimaryKey(id);
    }
    @Override
    public Map queryAllByPage(Integer page, Integer rows) {
        Map map = new HashMap<>();
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        //轮播图分页查
        List<Guru> bannerList = guruDao.selectByRowBounds(null,new RowBounds(begin,rows));
        //查询出总条数
        Integer records = guruDao.selectCount(null);
        //总页数
        Integer total=records%rows==0 ? records/rows : records/rows+1;
        map.put("total",total);     //总页数
        map.put("records",records); //总条数
        map.put("page",page);       //当前页
        map.put("rows",bannerList);//轮播图分页查
        return map;
    }

    @Override
    public List<Guru> queryAll() {
        List<Guru> guruList = guruDao.selectAll();
        return guruList;
    }

    @Override
    public Map queryAllMap() {
        Map<String, Object> map = new HashMap<>();
        List<Guru> guruList = guruDao.selectAll();
        map.put("rows",guruList);
        return map;
    }



    @Override
    public Guru insert(Guru guru) {
        guru.setId(UUID.randomUUID().toString());
        this.guruDao.insert(guru);
        return guru;
    }

    @Override
    public Guru update(Guru guru) {
        this.guruDao.updateByPrimaryKeySelective(guru);
        return this.queryById(guru.getId());
    }

    @Override
    public boolean deleteById(String id) {
        return this.guruDao.deleteByPrimaryKey(id) > 0;
    }

    //批量删除
    @Override
    public int deleteByIdList(List<String> list) {
        int i = guruDao.deleteByIdList(list);
        return i;
    }
}