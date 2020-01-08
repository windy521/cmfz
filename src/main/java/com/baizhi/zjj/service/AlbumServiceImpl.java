package com.baizhi.zjj.service;

import com.baizhi.zjj.dao.AlbumDao;
import com.baizhi.zjj.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AlbumServiceImpl implements AlbumService {
    @Resource
    private AlbumDao albumDao;

    @Override
    public Album queryById(String id) {
        return this.albumDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String,Object> queryAllByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        //轮播图分页查
        List<Album> albumList = albumDao.selectByRowBounds(new Album(),new RowBounds(begin,rows));
        //查询出总条数
        Integer records = albumDao.selectCount(new Album());
        //总页数
        Integer total=records%rows==0 ? records/rows : records/rows+1;
        map.put("total",total);     //总页数
        map.put("records",records); //总条数
        map.put("page",page);       //当前页
        map.put("rows",albumList);//轮播图分页查
        return map;

    }

    /**
     * 新增数据
     *
     * @param album 实例对象
     * @return 实例对象
     */
    @Override
    public Album insert(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        this.albumDao.insert(album);
        return album;
    }

    /**
     * 修改数据
     *
     * @param album 实例对象
     * @return 实例对象
     */
    @Override
    public Album update(Album album) {
/*        if(album.getUrl()==""){
            album.setUrl(null);
            this.albumDao.updateByPrimaryKeySelective(album);
        }else {
            this.albumDao.updateByPrimaryKeySelective(album);
        }*/
        this.albumDao.updateByPrimaryKeySelective(album);
        return this.queryById(album.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.albumDao.deleteByPrimaryKey(id) > 0;
    }
    //批量删除
    @Override
    public int deleteByIdList(List<String> list) {
        int i = albumDao.deleteByIdList(list);
        return i;
    }
}