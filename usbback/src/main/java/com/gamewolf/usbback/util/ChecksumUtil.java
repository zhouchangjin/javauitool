package com.gamewolf.usbback.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class ChecksumUtil {
	
	public static String getChecksum(String filePath, String algorithm) throws IOException, NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(algorithm);
        //byte[] hash = Files.readAllBytes(path); // 读取所有字节到内存中（适用于小文件）
        
        FileInputStream fis=new FileInputStream(new File(filePath));
        byte[] buffer = new byte[60*1024*1024]; // 设置缓冲区大小，根据需要调整大小。
        int readCount=0;
        while ((readCount=fis.read(buffer)) > 0) { // 使用DigestInputStream或手动读取文件并更新摘要。
            md.update(buffer,0,readCount); // 更新摘要信息。
        }
        byte[] digest = md.digest(); // 完成哈希计算并获取结果。
       // return Base64.getEncoder().encodeToString(digest); // 返回Base64编码的哈希值。
        return toHex(digest);
    }
	
	public static String getChecksumWindows(String filePath,String algorithm) {
		Runtime runtime=Runtime.getRuntime();
		String changeForm="";
		if(algorithm.equals("SHA-256")) {
			changeForm="SHA256";
		}
		String cmd="certutil -hashfile \""+filePath+"\" "+changeForm;
		try {
			Process p=runtime.exec(cmd);
			try(BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()))){
				String out=br.readLine();
				out=br.readLine();
				return out;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String toHex(byte[] data) {
		StringBuffer sb=new StringBuffer();
		for(byte b:data) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
