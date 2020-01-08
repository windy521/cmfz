package com.baizhi.zjj.dao;

import com.baizhi.zjj.entity.Log;

import java.util.List;

public interface LogDao {
    public void addLog(Log log);

    public List<Log> selectAllLog();
}
