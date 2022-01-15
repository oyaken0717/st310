package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OrderForm;

@Controller
@RequestMapping("/outSideLine")
public class OutSideLineContoller {
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	@RequestMapping("/toOutSideLine")
	public String toOutSideLine() {
		return "out_side_line";
	}
}
