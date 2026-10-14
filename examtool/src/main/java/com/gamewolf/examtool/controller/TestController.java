package com.gamewolf.examtool.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.gamewolf.examtool.data.DataTool;
import com.gamewolf.examtool.data.FillPart;
import com.gamewolf.examtool.data.FillTest;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TestController {
	
	FillTest test;
	
	@FXML
	TextFlow testText;
	
	@FXML
	VBox answPane;
	
	int cnt;
	
    List<FillPart> current;
    
    List<TextField> currentTextField;
	
	@FXML
	void initialize() {
		currentTextField=new ArrayList<TextField>();
		System.out.println("helloworld");
		
		String path=getClass().getResource("/题库.txt").getPath();
		
		test=DataTool.read(path);
		cnt=test.getCnt();
		int rnd=new Random().nextInt(cnt);
		List<FillPart> t=test.getTest(rnd);
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
		

		int rnd=new Random().nextInt(cnt);
		choose(rnd);
	}
	
	private void checkAnswer(List<FillPart> current2) {
		
		List<String> correctAnswer=new ArrayList<String>();
		for(FillPart f:current2) {
			if(f.isFillPart()) {
				correctAnswer.add(f.getText());
			}
		}
		String or=current2.stream().map(e->e.getText()).collect(Collectors.joining(""));
		System.out.println(or);
		for(int i=0;i<currentTextField.size();i++) {
			String answer=currentTextField.get(i).getText();
			System.out.print(answer+":"+correctAnswer.get(i));
			System.out.print(answer.equals(correctAnswer.get(i))?"\033[32m正确":"\033[31m错误");
			System.out.println("\033[30m");
		}
		
	}

	@FXML
	void preTest() {
		System.out.println("previous");
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

}
