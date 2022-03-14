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
			result.rejectValue("selectedTopSpace", null, "8.5cm以内にしてください");
			return "top_space";
		}
		
		outSideLine = decideOutSideLine(outSideLine, candidatedTopSpace);
		sessionForm.setOutSideLine(outSideLine);
		
		session.setAttribute("sessionForm", sessionForm);
		return "redirect:/underSpace/toUnderSpace";
	}

	private boolean notMatch(String candidatedTopSpace) {

		boolean notMatch = false;
		double topSpace = Double.parseDouble(candidatedTopSpace);
		double outLine = 8.5;
		
		//■入力した間の差が8.5cmよりも長い時(正規グリップよりも底に近くなるのでアウト)
		if (topSpace > outLine) {
			//start画面に遷移			
			notMatch = true;
		}
		return notMatch;
	}

	
	private String decideOutSideLine(String candidatedOutSideLine, String candidatedTopSpace) {

		double outSideLine = Double.parseDouble(candidatedOutSideLine);
		double topSpace = Double.parseDouble(candidatedTopSpace);
		
		double outLine = 8.5;
		
		double gripLine = 5.5;
		double middleLine = 6.8;
		
		String decidedOutSideLine = null;
		
		//■入力した間の差が8.5cmよりも長い時(正規グリップよりも底に近くなるのでアウト)
		if (topSpace > outLine) {
			//start画面に遷移			
			return null;
		}

		//■topSpaceの値の調整
		//■1.正規グリップの長さ		
		if (outSideLine == gripLine) {
			if (topSpace > 3.2) {
				//最長-正規グリップの間(3.2cm)よりも長い場合は引き算が必要な為topSpaceを調整
				topSpace = topSpace - 3.2;
			}else {
				//最長-正規グリップの間が3.2cm以下は最小の長さに影響しないので0を格納する。
				topSpace = 0;
			}
		}
		
		//■2.中間の長さ
		if (outSideLine == middleLine) {
			if (topSpace > 1.7) {
				//最長-中間の間(1.7cm)よりも長い場合は引き算が必要な為topSpaceを調整
				topSpace = topSpace - 1.7;
			}else {
				//最長-中間の間が1.7cm以下は中間の長さに影響しないので0を格納する。
				topSpace = 0;
			}
		}
		
		//■3.外側の長さは特に微調整なし
		outSideLine = outSideLine - topSpace;

		decidedOutSideLine = String.valueOf(outSideLine);
		return decidedOutSideLine;
	}
}
