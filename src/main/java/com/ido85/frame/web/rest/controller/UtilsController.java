package com.ido85.frame.web.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/utils")
public class UtilsController {

	/**
	 * 获取系统时间，毫秒数
	 * @return
	 */
	@RequestMapping("systime")
	public long sysTime(){
		return System.currentTimeMillis();
	}
}
