package com.baizhi.zjj.service;

import com.baizhi.zjj.dao.ChapterDao;
import com.baizhi.zjj.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Resource
    private ChapterDao chapterDao;

    @Override
    public Chapter queryById(String id) {
        return this.chapterDao.selectByPrimaryKey(id);
    }

    @Override
    public Map<String,Object> queryAllByPage(Integer page, Integer rows,String albumId) {
        Map<String, Object> map = new HashMap<>();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        //查询出总条数
        Integer records = chapterDao.selectCount(chapter);
        //获取数据库中的起始条
        Integer begin=(page-1)*rows;
        //轮播图分页查
        List<Chapter> chapterList = chapterDao.selectByRowBounds(chapter,new RowBounds(begin,rows));
        //List<Chapter> chapterList = chapterDao.selectAllByAlbumId(begin,rows,albumId);
        System.out.println("chapterList = " + chapterList);

        //Integer records = chapterDao.selectCountByAlbumId(albumId);

        //总页数
        Integer total=records%rows==0 ? records/rows : records/rows+1;
        map.put("total",total);     //总页数
        map.put("records",records); //总条数
        map.put("page",page);       //当前页
        map.put("rows",chapterList);//轮播图分页查
        return map;

    }


    /**
     * 新增数据
     *
     * @param chapter 实例对象
     * @return 实例对象
     */
    @Override
    public Chapter insert(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateTime(new Date());
        this.chapterDao.insert(chapter);
        return chapter;
    }

    /**
     * 修改数据
     *
     * @param chapter 实例对象
     * @return 实例对象
     */
    @Override
    public Chapter update(Chapter chapter) {
        if(chapter.getUrl()==""){
            chapter.setUrl(null);
            this.chapterDao.updateByPrimaryKeySelective(chapter);
        }else {
            this.chapterDao.updateByPrimaryKeySelective(chapter);
        }
        return this.queryById(chapter.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.chapterDao.deleteByPrimaryKey(id) > 0;
    }
    //批量删除
    @Override
    public int deleteByIdList(List<String> list) {
        int i = chapterDao.deleteByIdList(list);
        return i;
    }
}


