package com.example.form;

public class OrderForm {
	
	/** 外側の長さ */
	private String outSideLine;
	
	/** バーナーと遮熱版の間の長さ */
	private String topSpace;
	
	/** バーナーと遮熱版の間の長さ(入力) */
	private String inputTopSpace;
	
	/** バーナーと遮熱版の間の長さ(選択) */
	private String selectedTopSpace;
	
	/** チューブ(グリップ)と遮熱版の間の長さ */
	private String underSpace;
	
	/** 内側の長さ */
	private String inSideLine;
	
	public String getOutSideLine() {
		return outSideLine;
	}

	public void setOutSideLine(String outSideLine) {
		this.outSideLine = outSideLine;
	}

	public String getTopSpace() {
		return topSpace;
	}

	public void setTopSpace(String topSpace) {
		this.topSpace = topSpace;
	}

	public String getInputTopSpace() {
		return inputTopSpace;
	}

	public void setInputTopSpace(String inputTopSpace) {
		this.inputTopSpace = inputTopSpace;
	}

	public String getSelectedTopSpace() {
		return selectedTopSpace;
	}

	public void setSelectedTopSpace(String selectedTopSpace) {
		this.selectedTopSpace = selectedTopSpace;
	}

	public String getUnderSpace() {
		return underSpace;
	}

	public void setUnderSpace(String underSpace) {
		this.underSpace = underSpace;
	}

	public String getInSideLine() {
		return inSideLine;
	}

	public void setInSideLine(String inSideLine) {
		this.inSideLine = inSideLine;
	}

}
