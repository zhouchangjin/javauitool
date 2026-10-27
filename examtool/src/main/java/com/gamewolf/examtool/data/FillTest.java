package com.gamewolf.examtool.data;

import java.util.ArrayList;
import java.util.List;


public class FillTest {
	
	public List<List<FillPart>> tests;
	
	public List<QuestionAndAnswer> testQnA;
	
	public FillTest() {
		 tests=new ArrayList<List<FillPart>>();
		 testQnA=new ArrayList<QuestionAndAnswer>();
	}
	
	public int getQnATestCnt() {
		return testQnA.size();
	}
	
	public void addQnA(QuestionAndAnswer qna) {
		testQnA.add(qna);
	}
	
	public QuestionAndAnswer getQnA(int i) {
		return testQnA.get(i);
	}
	
	public void addTest(List<FillPart> t) {
		tests.add(t);
	}
	
	public List<FillPart> getTest(int id) {
		return tests.get(id);
	}
	
	public int getCnt() {
		return tests.size();
	}
	
	public void clear() {
		tests.clear();
		testQnA.clear();
	}
	
	public void init(List<List<FillPart>> all) {
		tests.addAll(all);
	}
	
	public void init(FillTest other) {
		init(other.getAll());
		testQnA.addAll(other.getAllQnA());
	}
	
	public List<List<FillPart>> getAll(){
		return tests;
	}
	
	public List<QuestionAndAnswer> getAllQnA(){
		return testQnA;
	}
	
	public String toAnswerAll(int i) {
		StringBuffer sb=new StringBuffer();
		var t=tests.get(i);
		for(FillPart f:t) {
			if(f.isFillPart()) {
				sb.append(" ["+f.getText()+"] ");
			}else {
				sb.append(f.getText());
			}
			
		}
		return sb.toString();
	}

	public String toTestString(int i) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		var t=tests.get(i);
		for(FillPart f:t) {
			if(f.isFillPart()) {
				sb.append("_____________");
			}else {
				sb.append(f.getText());
			}
			
		}
		return sb.toString();
	}
	

}
