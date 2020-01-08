package com.baizhi.zjj.controller;


import com.baizhi.zjj.util.CreateValidateCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ImageController {
	@RequestMapping("createImg")
	public String createImg(HttpSession session, HttpServletResponse response) throws IOException {
		CreateValidateCode vcode = new CreateValidateCode();
		String code = vcode.getCode(); // 随机验证码
		vcode.write(response.getOutputStream()); // 把图片输出client
		// 把随机验证码 存入session
		session.setAttribute("serverCode", code);
		return null;  // 不需要页面跳转
	}

}
