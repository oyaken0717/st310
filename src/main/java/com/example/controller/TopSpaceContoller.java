package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/topSpace")
public class TopSpaceContoller {

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public OrderForm setUpForm() {
		return new OrderForm();
	}
	
	@RequestMapping("/toTopSpace")
	public String toTopSpace() {
		
		Integer sessionId = (Integer)session.getAttribute("sessionId");
		if (Objects.isNull(sessionId)) {
			return "redirect:/";
		}
		
		return "top_space";
	}
	@RequestMapping("/topSpaceRegister")
	public String topSpaceRegister(@Validated OrderForm form, BindingResult result, RedirectAttributes redirectAttributes) {

		String inputTopSpace = form.getInputTopSpace();
		String selectedTopSpace = form.getSelectedTopSpace();
		
		OrderForm sessionForm = (OrderForm) session.getAttribute("sessionForm");
		String outSideLine = sessionForm.getOutSideLine();
		String candidatedTopSpace = null;
		
		//未入力チェック
		if ( "".equals(inputTopSpace) && 
			 Objects.isNull(selectedTopSpace) ) {
			result.rejectValue("selectedTopSpace", null, "選択されていません");
			return "top_space";
		}
		//両方とも入力チェック
		if ( !"".equals(inputTopSpace) && 
			 !Objects.isNull(selectedTopSpace) ) {
			 result.rejectValue("selectedTopSpace", null, "複数選択されています");
			 return "top_space";
		}
		
		//値の格納		
		if ( !"".equals(inputTopSpace) ) {
			candidatedTopSpace = inputTopSpace;
		}
		if ( !Objects.isNull(selectedTopSpace) ) {
			candidatedTopSpace = selectedTopSpace;
		}

		if ( notMatch(candidatedTopSpace) ) {
			result.rejectValue("selectedTopSpace", null, "0.7cm以内にしてください");
			return "top_space";
		}
		
		outSideLine = decideOutSideLine(outSideLine, candidatedTopSpace);
		sessionForm.setOutSideLine(outSideLine);
		
		session.setAttribute("sessionForm", sessionForm);
		return "redirect:/insideLine/toInsideLine";
	}

	private boolean notMatch(String candidatedTopSpace) {

		boolean notMatch = false;
		double topSpace = Double.parseDouble(candidatedTopSpace);
		double outLine = 0.7;
		
		//■入力した間の差が8.5cmよりも長い時(正規グリップよりも底に近くなるのでアウト)
		if (topSpace > outLine) {
			//start画面に遷移			
			notMatch = true;
		}
		return notMatch;
	}

	
	private String decideOutSideLine(String outSideLine, String topSpace) {

		ArrayList<String> measuredList = new ArrayList<>( Arrays.asList("0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7") );
		ArrayList<String> resultList   = new ArrayList<>( Arrays.asList("8.5", "8.4", "8.3", "8.2", "8.1", "8.0", "7.8", "7.6") );

		for (int i = 0; i < measuredList.size(); i++) {
			if ( measuredList.get(i).equals(topSpace) ) {
				outSideLine = resultList.get(i);
			}
		}
		
		return outSideLine;
	}
}
