package com.gamewolf.usbback.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.gamewolf.usbback.controller.task.ChecksumTask;
import com.gamewolf.usbback.controller.task.ChecksumValidateTask;
import com.gamewolf.usbback.util.ChecksumUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class CheckController {
	
	@FXML
	ComboBox<String> checkTypeCombo;
	
	@FXML
	TextField folderTextField;
	
	@FXML
	ListView<String> logList;
	
	ObservableList<String> logs;
	
	@FXML
	void initialize() {
		System.out.println("===========");
		
		ObservableList<String> options = FXCollections.observableArrayList(
		        "SHA-256","md5","sha1","crc"
		    );
		logs = FXCollections.observableArrayList();
		checkTypeCombo.setItems(options);
		logList.setItems(logs);
	}
	
	@FXML
	void chooseFolder() {
		System.out.println("chooseFolder");
		DirectoryChooser directoryChooser=new DirectoryChooser();
		File folder=directoryChooser.showDialog(null);
		if(folder!=null) {
			String path=folder.getAbsolutePath();
			folderTextField.setText(path);
		}
	}
	
	@FXML
	void genChecksum() {
		System.out.println("genChecksum");
		String type=checkTypeCombo.getValue();
		String folder=folderTextField.getText();
		System.out.println(type+"=="+folder);
		String checkFile=folder+"/folder."+type+".gw_chksum";
		System.out.println(checkFile);
		File folderCheck=new File(checkFile);
		if(folderCheck.exists()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("错误");
			alert.setHeaderText("已有旧的校验文件");
			alert.setContentText("已有旧的校验文件");
			alert.showAndWait();
			
			return;
		}else {
			ChecksumTask task=new ChecksumTask(type, folder, logs);
			Thread t=new Thread(task);
			t.start();
		}
		
	}
	
	@FXML
	void checkFile() {
		logs.clear();
		String type=checkTypeCombo.getValue();
		String folder=folderTextField.getText();
		String checkFile=folder+"/folder."+type+".gw_chksum";
		File folderCheck=new File(checkFile);
		if(folderCheck.exists()) {
			ChecksumValidateTask task=new ChecksumValidateTask(type, folder, logs);
			Thread t=new Thread(task);
			t.start();
		}
		
	}
}
