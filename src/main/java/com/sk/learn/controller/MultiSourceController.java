package com.sk.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiSourceController {
	
	@Autowired MultiSourceService multiSourceService;

	
	//둘다 실패
	@GetMapping("test1")
	public String test1() {
		multiSourceService.test1();
		return "test1";
	}
	
	//mobile만 성공
	@GetMapping("test2")
	public String test2() {
		multiSourceService.test2();
		return "test2";
	}
	
	//hr만 성공
	@GetMapping("test3")
	public String test3() {
		multiSourceService.test3();
		return "test3";
	}
	
	//모두 성공
	@GetMapping("test4")
	public String test4() {
		multiSourceService.test4();
		return "test2";
	}
}
