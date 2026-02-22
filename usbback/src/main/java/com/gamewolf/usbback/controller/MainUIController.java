package com.gamewolf.usbback.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class MainUIController {
	
	@FXML
	Accordion accorPane;
	
	@FXML
	ScrollPane rightPane;
	
	VBox checkVbox;
	
	CheckController checkController;
	

	@FXML
	void showCheckPanel(){
		System.out.println("=======check========");
		
	}
	
	@FXML
	void initialize() {
		System.out.println("initialize");
		
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("crcCheckPanel.fxml"));
		try {
			checkVbox=loader.load();
			checkController=loader.getController();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	void selectCheckFile() {
		String sel=accorPane.getTypeSelector();
		TitledPane ti=accorPane.expandedPaneProperty().getValue();
		if(ti!=null) {
			String title=ti.getText();
			if(title.equals("文件校验码")) {
				System.out.println("切换panel");
				rightPane.setContent(checkVbox);
			}
		}
	}
}
