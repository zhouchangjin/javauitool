package com.gamewolf.usbback.controller.task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.gamewolf.usbback.util.ChecksumUtil;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class ChecksumTask implements Runnable{
	String type;
	String folder;
	ObservableList<String> dataList;
	
	public ChecksumTask(String type,String folder,ObservableList<String> dataList){
		this.type=type;
		this.folder=folder;
		this.dataList=dataList;
	}

	@Override
	public void run() {
		checkFolder(type, folder);		
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
					Platform.runLater(()->{
						dataList.add(line);
					});
					//System.out.println(line);
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
