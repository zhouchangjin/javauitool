package com.gamewolf.usbback.controller.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.gamewolf.usbback.util.ChecksumUtil;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class ChecksumValidateTask implements Runnable{
	
	String type;
	String folder;
	ObservableList<String> dataList;
	
	public ChecksumValidateTask(String type,String folder,ObservableList<String> dataList){
		this.type=type;
		this.folder=folder;
		this.dataList=dataList;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		checkFolder();
	}
	
	private void addLog(String log) {
		Platform.runLater(()->{
			dataList.add(log);
		});
	}
	
	private void checkFolder() {
		String checkFile=folder+"/folder."+type+".gw_chksum";
		try {
			BufferedReader bw=new BufferedReader(new FileReader(checkFile));
			String line=null;
			while((line=bw.readLine())!=null) {
				String parts[]=line.split(",");
				String fileName=parts[0];
				String checksum=parts[1];
				String log="扫描"+fileName+" 校验码"+checksum;
				addLog(log);
				
				File actualFile=new File(fileName);
				if(actualFile.exists()) {
					String checksumStr=ChecksumUtil.getChecksum(fileName,type);
					if(checksumStr.equals(checksum)) {
						
						addLog("文件校验码相同");
						
					}else {
						addLog("文件"+fileName+"校验码发生变化，可能发生数据掉电或病毒");
					}
				}else {
					log="文件"+fileName+"丢失";
					addLog(log);
				}
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
