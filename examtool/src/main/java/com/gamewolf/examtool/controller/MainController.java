package com.gamewolf.examtool.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

public class MainController {
	
	@FXML
	AnchorPane contentPane;
	
	AnchorPane testPane;
	
	TestController testController;
	
	@FXML
	void initialize() {
		System.out.println("======init=============");
		 final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/testPanel.fxml"));
		 try {
			testPane=loader.load();
			testController=loader.getController();
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
	 
	 @FXML
	 private void inputTestNo(ActionEvent event) {
		 System.out.println("======click=d============");
		TextInputDialog textInputDialog=new TextInputDialog();
		Optional<String> result=textInputDialog.showAndWait();
		result.ifPresent(e->{
			if(!e.isEmpty()) {
				testController.choose(Integer.parseInt(e));
			}
		});
	 }

}
