package com.gamewolf.examtool.controller;

import java.io.File;
import java.util.List;
import java.util.Random;

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
	
	
	
	@FXML
	void initialize() {
		System.out.println("helloworld");
		
		String path=getClass().getResource("/题库.txt").getPath();
		
		test=DataTool.read(path);
		cnt=test.getCnt();
		int rnd=new Random().nextInt(cnt);
		List<FillPart> t=test.getTest(rnd);
		for(FillPart f:t) {
			if(f.isFillPart()) {
				testText.getChildren().add(new Text("____________"));
			}else {
				testText.getChildren().add(new Text(f.getText()));
			}
			
		}
		
	}
	
	void genAnswerField(List<FillPart> t) {
		answPane.getChildren().clear();
		int cnt=0;
		for(FillPart f:t) {
			if(f.isFillPart()) {
				cnt++;
				HBox hbox=new HBox();
				hbox.getChildren().add(new Label(cnt+":"));
				hbox.getChildren().add(new TextField());
				hbox.setPadding(new Insets(20, 0, 0, 0));
				answPane.getChildren().add(hbox);
			}
		}
		
	}
	
	@FXML
	void nextTest() {
		System.out.println("next");
		
		testText.getChildren().clear();
		
		int rnd=new Random().nextInt(cnt);
		List<FillPart> t=test.getTest(rnd);
		for(FillPart f:t) {
			if(f.isFillPart()) {
				testText.getChildren().add(new Text("____________"));
			}else {
				testText.getChildren().add(new Text(f.getText()));
			}
			
		}
		
		genAnswerField(t);
	}
	
	@FXML
	void preTest() {
		System.out.println("previous");
	}

}
