package com.baizhi.zjj.service;

import com.baizhi.zjj.dao.AdminDao;
import com.baizhi.zjj.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    public Map<String, Object> isLogin(String username, String password, String clientCode, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        // 获取server上的验证码（从session中获取）
        String serverCode = session.getAttribute("serverCode").toString();
        System.out.println("server生成的验证码："+ serverCode);
        System.out.println("client输入的验证码："+ clientCode);
        if(!clientCode.equals(serverCode)){// 检查验证码，若不正确，回登录页
            System.out.println("验证码错误输入错误");
            map.put("msg","验证码错误");
        }else{
            //若正确则：
            // 1 获取client的数据
            // 2 调用业务类的方法，实现 “登录检查”功能
            Admin admin = adminDao.selectOne(new Admin(null,username,null));
            System.out.println(admin);
            // 3 根据结果，跳转
            if (admin == null){
                System.out.println("用户名不存在");
                map.put("msg","用户名不存在");
            }else {
                session.setAttribute("myAdmin", admin);
                if(password.equals(admin.getPassword())){
                    System.out.println("登陆成功");
                    map.put("msg","登陆成功");
                }else{
                    System.out.println("密码错误");
                    map.put("msg","密码错误");
                }
            }

        }

        return map;
    }
}
