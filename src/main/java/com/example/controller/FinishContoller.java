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
		
		String pictureNum = getPicture(sessionForm);
		model.addAttribute("pictureNum",pictureNum);
		
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

	private String getPicture(OrderForm sessionForm) {

		String outSideLine = sessionForm.getOutSideLine();
		String inSideLine = sessionForm.getInSideLine();
		StringBuilder pictureNum = new StringBuilder();
		
		switch (outSideLine) {
			case "5.5":
				pictureNum.append("1");
				break;
			case "6.8":
				pictureNum.append("2");
				break;
			case "9.1":
				pictureNum.append("4");
				break;
				
			default:
				pictureNum.append("3");
				break;
		}

		switch (inSideLine) {
			case "2.0":
				pictureNum.append("1");
				break;
			case "4.0":
				pictureNum.append("2");
				break;
			case "6.5":
				pictureNum.append("3");
				break;
				
			default:
				pictureNum.append("3");
				break;
		}

		return pictureNum.toString();
	}

}
