package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class StartContoller {
	
	@Autowired
	private HttpSession session;

	@RequestMapping("")
	public String toStart() {
		
		session.setAttribute("sessionId", session.getId().hashCode());
		
		return "start";
	}

}
