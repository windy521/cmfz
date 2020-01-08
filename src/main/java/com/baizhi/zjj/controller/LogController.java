package com.baizhi.zjj.controller;

import com.baizhi.zjj.entity.Log;
import com.baizhi.zjj.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {
	@Resource
	private LogService logService;

	@RequestMapping("queryAllLog")
	@ResponseBody
	public List<Log> queryAllLog() {
		return this.logService.queryAllLog();
	}



	
	
	
}
