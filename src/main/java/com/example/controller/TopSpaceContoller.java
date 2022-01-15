package com.example.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OrderForm;

@Controller
@RequestMapping("/topSpace")
public class TopSpaceContoller {
	
	@RequestMapping("/toTopSpace")
	public String toOutSideLine(@Validated OrderForm form, BindingResult result, Model model) {
		if (Objects.isNull(form.getOutSideLine())) {
			return "start";
		}
		return "top_space";
	}
}
