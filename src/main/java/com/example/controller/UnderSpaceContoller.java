package com.example.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.OrderForm;

@Controller
@RequestMapping("/underSpace")
public class UnderSpaceContoller {
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@RequestMapping("/toUnderSpace")
	public String toUnderSpace() {
		
		Integer sessionId = (Integer)session.getAttribute("sessionId");
		if (Objects.isNull(sessionId)) {
			return "redirect:/";
		}
			
		return "under_space";
	}
	
	@RequestMapping("/underSpaceRegister")
	public String underSpaceRegister(@Validated OrderForm form, BindingResult result, RedirectAttributes redirectAttributes) {
		
		boolean update = false;
		
		if ( Objects.isNull(form.getUnderSpace()) ) {
			result.rejectValue("underSpace", null, "選択されていません");
			return "under_space";
		}

		if ( notMatch(form) ) {
			return "redirect:/";
		}
		
		update = decideOutSideLine(form);
		if (!update) {
			result.rejectValue("underSpace", null, "外側の長さがマイナスになります");
			return "under_space";
		}
		
		return "redirect:/insideLine/toInsideLine";
	}
	
	private boolean notMatch(OrderForm form) {
		
		boolean notMatch = false;

		double underSpace = Double.parseDouble(form.getUnderSpace());;
		
		if ( 0.0 != underSpace  &&
			 0.5 != underSpace  &&
			 1.0 != underSpace  ){
				 notMatch = true;
				 return notMatch;
		}

		return notMatch;
	}
	private boolean decideOutSideLine(OrderForm form) {
		
		boolean update = false;

		OrderForm sessionForm = (OrderForm) session.getAttribute("sessionForm");
		double outSideLine = Double.parseDouble(sessionForm.getOutSideLine());
		double underSpace = Double.parseDouble(form.getUnderSpace());;
		String decidedOutSideLine = null;
		
		outSideLine = outSideLine - underSpace;
		if (outSideLine >= 0) {
			decidedOutSideLine = String.valueOf(outSideLine);
			sessionForm.setOutSideLine(decidedOutSideLine);			
			session.setAttribute("sessionForm", sessionForm);
			update = true;
		}
		return update;
	}

}
