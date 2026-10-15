package com.gamewolf.examtool.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.gamewolf.examtool.data.DataTool;
import com.gamewolf.examtool.data.ExamUtil;
import com.gamewolf.examtool.data.FillPart;
import com.gamewolf.examtool.data.FillTest;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TestController {
	
	FillTest test;
	
	FillTest testDb;

	String path=null;
	
	@FXML
	TextFlow testText;
	
	@FXML
	VBox answPane;
	
	int cnt;
	
    List<FillPart> current;
    
    List<TextField> currentTextField;
    
    boolean randMode=true;
    
    int pos=0;

	int score=0;
	int totalScore=0;

	private void loadTest(){
		if(currentTextField==null){
			currentTextField=new ArrayList<TextField>();
		}else{
			currentTextField.clear();
		}
		pos=0;
		testDb=DataTool.read(path);
		test=new FillTest();
		test.init(testDb);
		cnt=test.getCnt();
		List<FillPart> t=genTestField();
		genAnswerField(t);
	}
	
	@FXML
	void initialize() {

		System.out.println("helloworld");
		if(path==null){
			path=getClass().getResource("/题库.txt").getPath();
			loadTest();
		}

	}

	private List<FillPart> genTestField() {
		int rnd=new Random().nextInt(cnt);
		List<FillPart> t=test.getTest(rnd);
		current=t;
		for(FillPart f:t) {
			if(f.isFillPart()) {
				Text blank=new Text("____________");
				testText.getChildren().add(blank);
			}else {
				Text text=new Text(f.getText());
				//text.setStyle("-fx-fill: #87CEEB");
				testText.getChildren().add(text);
			}

		}
		return t;
	}

	void genAnswerField(List<FillPart> t) {
		answPane.getChildren().clear();
		int cnt=0;
		for(FillPart f:t) {
			if(f.isFillPart()) {
				cnt++;
				HBox hbox=new HBox();
				hbox.getChildren().add(new Label(cnt+":"));
				TextField tf=new TextField();
				currentTextField.add(tf);
				hbox.getChildren().add(tf);
				hbox.setPadding(new Insets(10, 0, 0, 0));
				answPane.getChildren().add(hbox);
			}
		}
		
	}
	
	@FXML
	void nextTest() {
		System.out.println("next");
		
		checkAnswer(current);
		
		if(randMode) {
			int rnd=new Random().nextInt(cnt);
			choose(rnd);
		}else {
			if(pos<test.getCnt()-1) {

				pos++;
				System.out.println(pos);
				choose(pos);
			}
		}
		
	}
	
	private void checkAnswer(List<FillPart> current2) {
		
		List<String> correctAnswer=new ArrayList<String>();
		for(FillPart f:current2) {
			if(f.isFillPart()) {
				correctAnswer.add(f.getText());
			}
		}
		String or=current2.stream().map(e->{
			if(e.isFillPart()){
				return "\033[32m"+e.getText();
			}else{
				return "\033[36m"+e.getText();
			}
		}).collect(Collectors.joining(""));
		System.out.println("\033[36m"+or);
		for(int i=0;i<currentTextField.size();i++) {
			String answer=currentTextField.get(i).getText();
			System.out.print(answer+":"+correctAnswer.get(i));
			System.out.print(answer.equals(correctAnswer.get(i))?"\033[32m 正确":"\033[31m 错误");
			System.out.println("\033[36m");
		}
		
	}

	@FXML
	void preTest() {
		System.out.println("previous");
		if(!randMode){
			if(pos>0){
				pos--;
				choose(pos);
			}
		}
	}

	public void choose(int testNo) {
		// TODO Auto-generated method stub
		currentTextField.clear();
		testText.getChildren().clear();
		List<FillPart> t=test.getTest(testNo);
		current=t;
		for(FillPart f:t) {
			if(f.isFillPart()) {
				testText.getChildren().add(new Text("____________"));
			}else {
				testText.getChildren().add(new Text(f.getText()));
			}
			
		}
		
		genAnswerField(t);
	}

	public void examGen() {
		randMode=false;
		test.clear();
		int n=testDb.getCnt();
		int m=n>10?10:n;
		List<Integer> testNos=ExamUtil.pickByShuffle(n, m);
		for(Integer i:testNos) {
			var t=testDb.getTest(i);
			test.addTest(t);
		}
		pos=0;
		choose(0);
		ExamUtil.genWord(test, "d:/考试题.docx");
	}

	public void loadFile(String path) {
		this.path=path;
		loadTest();
	}
}
