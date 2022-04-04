package com.example.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.OrderForm;

@Controller
@RequestMapping("/outSideLine")
public class OutSideLineContoller {
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}

	@RequestMapping("/toOutSideLine")
	public String toOutSideLine() {
		
		Integer sessionId = (Integer)session.getAttribute("sessionId");
		if (Objects.isNull(sessionId)) {
			return "redirect:/";
		}
		
		return "out_side_line";
	}
	
	@RequestMapping("/outSideLineRegister")
	public String outSideLineRegister(@Validated OrderForm form, BindingResult result, RedirectAttributes redirectAttributes) {

		String outSideLine = form.getOutSideLine(); 
		
		if ( Objects.isNull(outSideLine) ) {
			result.rejectValue("outSideLine", null, "選択されていません");
			return "out_side_line";
		}
		
		if ( notMatch(form) ) {
			return "redirect:/";
		}
		
		session.setAttribute("sessionForm", form);
		
		if ("9.1".equals(outSideLine)) {
			return "redirect:/topSpace/toTopSpace";
		}
		return "redirect:/insideLine/toInsideLine";
	}

	private boolean notMatch(OrderForm form) {
		
		String outSideLine = form.getOutSideLine();
		boolean notMatch = false;
		
		if ( !"5.5".equals(outSideLine)  &&
			 !"6.8".equals(outSideLine)  &&
			 !"9.1".equals(outSideLine)  ){
			notMatch = true;
		}
		
		return notMatch;
	}

}
