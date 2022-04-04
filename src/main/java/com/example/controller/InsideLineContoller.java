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
@RequestMapping("/insideLine")
public class InsideLineContoller {
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@RequestMapping("/toInsideLine")
	public String toInsideLine() {
		
		Integer sessionId = (Integer)session.getAttribute("sessionId");
		if (Objects.isNull(sessionId)) {
			return "redirect:/";
		}
		
		return "in_side_line";
	}
	
	@RequestMapping("/insideLineRegister")
	public String insideLineRegister(@Validated OrderForm form, BindingResult result, RedirectAttributes redirectAttributes) {

		if ( Objects.isNull(form.getInSideLine()) ) {
			result.rejectValue("inSideLine", null, "選択されていません");
			return "in_side_line";
		}
		
		if ( notMatch(form) ) {
			 return "redirect:/";
		}
		
		return "redirect:/finish/toFinish";
	}
	
	private boolean notMatch(OrderForm form) {
		
		boolean notMatch = false;
		String inSideLine = form.getInSideLine();
		OrderForm sessionForm = (OrderForm) session.getAttribute("sessionForm");
		
		if ( !"2.0".equals(inSideLine)  &&
			 !"4.0".equals(inSideLine)  &&
			 !"6.5".equals(inSideLine)  ){
			 notMatch = true;
		}
		
		sessionForm.setInSideLine(inSideLine);			
		session.setAttribute("sessionForm", sessionForm);

		return notMatch;
	}
}