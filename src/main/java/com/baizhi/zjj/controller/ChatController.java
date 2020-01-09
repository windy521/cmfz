package com.baizhi.zjj.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baizhi.zjj.service.AdminService;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("chat")
public class ChatController {

    @RequestMapping("sendChat")
    @ResponseBody
    public void sendChat(String message){
        //每添加一个用户，查询用户当前统计信息，使用goEasy推送
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-e16fb777a6b2422fa18ac579f346cf48");
        //GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-69489dfd130b4b8195d4b1a675650c13");
        String sss = "帅比俊："+ message;
        String jsonString = JSONUtils.toJSONString(sss);
        System.out.println("发送内容："+jsonString);
        goEasy.publish("cmfz", jsonString);
    }

}
