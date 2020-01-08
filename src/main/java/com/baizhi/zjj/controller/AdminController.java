package com.baizhi.zjj.controller;

import com.baizhi.zjj.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(String username, String password, String clientCode, HttpSession session){
        Map<String, Object> login = adminService.isLogin(username, password, clientCode, session);
        return login;
    }

    @RequestMapping("loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("myAdmin");
        System.out.println("账户已注销");
        return "redirect:/jsp/login.jsp";
    }
}
