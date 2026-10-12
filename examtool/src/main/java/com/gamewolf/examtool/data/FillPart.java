package com.gamewolf.examtool.data;

public class FillPart {
	
	String text;
	boolean isFillPart;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		//this.text = text;
		this.text=text.replace("[", "").replace("]", "");
	}
	public boolean isFillPart() {
		return isFillPart;
	}
	public void setFillPart(boolean isFillPart) {
		this.isFillPart = isFillPart;
	}
	
	
	public String toString() {
		return text;
	}
	

}
