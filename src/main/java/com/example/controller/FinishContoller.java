package com.example.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.OrderForm;

@Controller
@RequestMapping("/finish")
public class FinishContoller {
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@RequestMapping("/toFinish")
	public String toFinish(Model model) {
		
		Integer sessionId = (Integer)session.getAttribute("sessionId");
		if (Objects.isNull(sessionId)) {
			return "redirect:/";
		}
		session.removeAttribute("sessionId");
		
		OrderForm sessionForm = (OrderForm) session.getAttribute("sessionForm");
		model.addAttribute("orderForm",sessionForm);
		session.removeAttribute("sessionForm");

		double answer = getAnswer(sessionForm);
		
		model.addAttribute("answer",answer);
		
		return "finish";
	}
	
	private double getAnswer(OrderForm sessionForm) {

		double outSideLine = Double.parseDouble(sessionForm.getOutSideLine());
		double inSideLine = Double.parseDouble(sessionForm.getInSideLine());
		double bottomSideLine = 1.5;
		
		double answer = outSideLine + bottomSideLine +inSideLine; 
		answer = ((double)Math.round(answer * 10))/10;
		
		return answer;

	}

}
