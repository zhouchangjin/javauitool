package com.gamewolf.examtool.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTool {

	public static FillTest read(String path) {
		FillTest ft=new FillTest();
		
		try {
			BufferedReader br=new BufferedReader(new FileReader(new File(path)));
			String line=null;
			
			while((line=br.readLine())!=null){
				List<FillPart> parts=parseLine(line);
				ft.addTest(parts);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ft;
	}

	private static List<FillPart> parseLine(String input) {
			List<FillPart> l=new ArrayList<FillPart>();
		 if (input == null || input.isEmpty()) {
			 	return List.of();
	        }

	        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
	        Matcher matcher = pattern.matcher(input);
	        String cur=input;
	        // 循环查找所有匹配项
	        while (matcher.find()) {
	            String content = matcher.group(1);
	            int front=cur.indexOf("["+content);
	            String frontStr=cur.substring(0,front+1);
	            if(!frontStr.isEmpty()) {
	            	FillPart f=new FillPart();
	            	f.setFillPart(false);
	            	f.setText(frontStr);
	            	l.add(f);
	            }
	            FillPart f=new FillPart();
	            f.setFillPart(true);
	            f.setText(content);
	            l.add(f);
	            cur=cur.substring(front+1+content.length());
	        }
	        if(!cur.isEmpty()) {
	        	FillPart f=new FillPart();
	        	f.setFillPart(false);
	        	f.setText(cur);
	        	l.add(f);
	        }
	        return l;

	}
	
	public static List<String> extractBracketContents(String input) {
        List<String> results = new ArrayList<>();
        
        if (input == null || input.isEmpty()) {
            return results;
        }

        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(input);

        // 循环查找所有匹配项
        while (matcher.find()) {
            // group(1) 获取第一个捕获组的内容，即括号内的部分
            // 如果希望包含括号本身，使用 group(0)
            String content = matcher.group(1);
            results.add(content);
        }

        return results;
    }

}
