package com.gamewolf.usbback.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.gamewolf.usbback.controller.task.ChecksumTask;
import com.gamewolf.usbback.util.ChecksumUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
		
		ChecksumTask task=new ChecksumTask(type, folder, logs);
		Thread t=new Thread(task);
		t.start();
		//checkFolder(type,folder);
		
	}

	private void checkFolder(String type, String folderPath) {
		File folder=new File(folderPath);
		File[] files=folder.listFiles();
		List<String> checksum=new ArrayList<>();
		for(File file:files) {
			String path=file.getAbsolutePath();
			if(file.isDirectory()) {
				checkFolder(type,path);
			}else if(path.endsWith("gw_chksum")) {
				continue;
			}else {
				try {
					String checksumStr=ChecksumUtil.getChecksum(path,type);//ChecksumUtil.getChecksum(path,type);
					String line=path+","+checksumStr;
					checksum.add(line);
					System.out.println(line);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		String path=folderPath+"/folder."+type+".gw_chksum";
		try(BufferedWriter bw=new BufferedWriter(new FileWriter(path))){
			for(String line:checksum) {
				bw.append(line);
				bw.newLine();
				bw.flush();
			}
		}catch(Exception e) {
			
		}
	}

}
