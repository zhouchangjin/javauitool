package com.gamewolf.examtool.data;

import java.util.ArrayList;
import java.util.List;

public class QuestionAndAnswer {
	
	boolean isChoiceType=false;
	
	String question;
	
	List<FillPart> answer;
	
	public QuestionAndAnswer() {
		answer=new ArrayList<FillPart>();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<FillPart> getAnswer() {
		return answer;
	}

	public void setAnswer(List<FillPart> answer) {
		this.answer = answer;
	}
	
	public void addAnswer(FillPart part) {
		this.answer.add(part);
	}
	
	

}
