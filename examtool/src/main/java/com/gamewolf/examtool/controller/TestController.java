package com.gamewolf.examtool.controller;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.gamewolf.examtool.data.DataTool;
import com.gamewolf.examtool.data.FillPart;
import com.gamewolf.examtool.data.FillTest;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TestController {
	
	FillTest test;
	
	@FXML
	TextFlow testText; 
	
	@FXML
	void initialize() {
		System.out.println("helloworld");
		
		String path=getClass().getResource("/题库.txt").getPath();
		
		test=DataTool.read(path);
		int cnt=test.getCnt();
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

}
