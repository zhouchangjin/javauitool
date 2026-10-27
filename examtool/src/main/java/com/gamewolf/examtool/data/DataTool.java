package com.gamewolf.examtool.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTool {

	public static FillTest read(String path) {
		FillTest ft=new FillTest();
		File file=new File(path);
		Reader r=null;
		
		if(!file.exists()) {
			StringBuffer sb=new StringBuffer();
			sb.append("中国古代四大发明是[造纸术],[印刷术],[火药],[指南针]");
			r=new StringReader(sb.toString());
		}else {
			try {
				r=new FileReader(new File(path));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try (BufferedReader br=new BufferedReader(r);){
			String line=null;
			while((line=br.readLine())!=null){
				if(line.contains("[")) {
					//填空题
					List<FillPart> parts=parseFileLine(line);
					ft.addTest(parts);
				}else if(line.startsWith("#Q#")) {
					String title=line.substring(3);
					///System.out.println("问题"+title);
					QuestionAndAnswer qna=parseQnA(title,br);
					ft.addQnA(qna);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ft;
	}

	private static QuestionAndAnswer parseQnA(String title, BufferedReader br) throws IOException {
		// TODO Auto-generated method stub
		QuestionAndAnswer q=new QuestionAndAnswer();
		q.setQuestion(title);
		String line=null;
		while((line=br.readLine())!=null) {
			if(line.startsWith("#F#")) {
				break;
			}else {
				FillPart fillPart=new FillPart();
				fillPart.setFillPart(true);
				fillPart.setText(line);
				q.addAnswer(fillPart);
			}
		}
		return q;
	}

	private static List<FillPart> parseFileLine(String input) {
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
