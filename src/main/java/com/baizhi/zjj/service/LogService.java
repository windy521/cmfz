package com.baizhi.zjj.service;


import com.baizhi.zjj.entity.Log;

import java.util.List;

public interface LogService {
    public void addLog(Log log);
    public List<Log> queryAllLog();
}
