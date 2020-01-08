package com.baizhi.zjj.service;

import com.baizhi.zjj.dao.UserDao;
import com.baizhi.zjj.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User queryById(String id) {
        return this.userDao.selectByPrimaryKey(id);
    }

    @Override
    public Map queryAllByPage(Integer page, Integer rows) {
        Map map = new HashMap<>();
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        //轮播图分页查
        List<User> bannerList = userDao.selectByRowBounds(null,new RowBounds(begin,rows));
        //查询出总条数
        Integer records = userDao.selectCount(null);
        //总页数
        Integer total=records%rows==0 ? records/rows : records/rows+1;
        map.put("total",total);     //总页数
        map.put("records",records); //总条数
        map.put("page",page);       //当前页
        map.put("rows",bannerList);//轮播图分页查
        return map;
    }

    @Override
    public Map queryAll() {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userDao.selectAll();
        map.put("rows",userList);
        return map;
    }



    @Override
    public User insert(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setRegistDate(new Date());
        this.userDao.insert(user);
        return user;
    }

    @Override
    public User update(User user) {
        this.userDao.updateByPrimaryKeySelective(user);
        return this.queryById(user.getId());
    }

    @Override
    public boolean deleteById(String id) {
        return this.userDao.deleteByPrimaryKey(id) > 0;
    }

    //批量删除
    @Override
    public int deleteByIdList(List<String> list) {
        int i = userDao.deleteByIdList(list);
        return i;
    }
}