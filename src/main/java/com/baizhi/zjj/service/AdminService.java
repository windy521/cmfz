package com.baizhi.zjj.service;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    public Map<String,Object> isLogin(String username, String password, String clientCode, HttpSession session);
}
