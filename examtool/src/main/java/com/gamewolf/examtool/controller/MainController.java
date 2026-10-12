package com.gamewolf.examtool.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class MainController {
	
	@FXML
	AnchorPane contentPane;
	
	AnchorPane testPane;
	
	@FXML
	void initialize() {
		System.out.println("======init=============");
		 final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/testPanel.fxml"));
		 try {
			testPane=loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 @FXML
	 private void newTest(ActionEvent event) {
	       
		   System.out.println("======click=============");
	       contentPane.getChildren().add(testPane);
	       
	 }

}
