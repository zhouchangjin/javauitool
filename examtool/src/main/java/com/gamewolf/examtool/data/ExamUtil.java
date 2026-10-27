package com.gamewolf.examtool.data;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ExamUtil {
	
	public static List<Integer> pickByShuffle(int n, int m) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        Collections.shuffle(list);
        return list.subList(0, m);
    }
	
	public static void genWord(FillTest test,String wordFolder,String wordName) {
		XWPFDocument document = new XWPFDocument();
		String wordPath=wordFolder+wordName+".docx";
        try {
            // 2. 添加标题
            addTitle(document, wordName, 1);
            
            addParagraph(document, "第一部分 填空题", false, false, "000000");
            
            for(int i=0;i<test.getCnt();i++) {
            	String testSTr="第"+(i+1)+"题：  "+test.toTestString(i);

                addParagraph(document, testSTr, false, false, "000000");
            }
            
            addParagraph(document, "第二部分 解答题", false, false, "000000");
            for(int i=0;i<test.getQnATestCnt();i++) {
            	String testSTr="第"+(i+1)+"题：  "+test.getQnA(i).getQuestion();
            	 addParagraph(document, testSTr, false, false, "000000");
            	QuestionAndAnswer q=test.getQnA(i);
            	List<FillPart> p=q.getAnswer();
            	int cnt=1;
            	for(FillPart f:p) {
            		addParagraph(document,cnt +")_________________________________",
            				false, false, "000000");
            		cnt++;
            	}
            }

            try (FileOutputStream out = new FileOutputStream(wordPath)) {
                document.write(out);
                System.out.println("Word 文档生成成功: " + wordPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	 /**
     * 添加标题
     */
    private static void addTitle(XWPFDocument document, String text, int level) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER); // 居中对齐
        
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(true);
        run.setFontSize(18);
        // 设置标题样式 Heading 1, Heading 2 等
        paragraph.setStyle("Heading" + level); 
    }

    /**
     * 添加段落
     */
    private static void addParagraph(XWPFDocument document, String text, boolean isBold, boolean isItalic, String colorHex) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT); // 左对齐

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(isBold);
        run.setItalic(isItalic);
        if (colorHex != null) {
            run.setColor(colorHex);
        }
        run.setFontSize(12);
        run.addCarriageReturn();
    }

	public static void genAnswWord(FillTest test, String wordFolder,String wordName) {
		XWPFDocument document = new XWPFDocument();
		String wordPath=wordFolder+wordName+".docx";	
        try {
            // 2. 添加标题
            addTitle(document, wordName, 1);
            
            addParagraph(document, "第一部分 填空题", false, false, "000000");
            
            for(int i=0;i<test.getCnt();i++) {
            	String testSTr="第"+(i+1)+"题：  "+test.toAnswerAll(i);
                addParagraph(document, testSTr, false, false, "000000");
            }
            addParagraph(document, "第二部分 解答题", false, false, "000000");
            for(int i=0;i<test.getQnATestCnt();i++) {
            	String testSTr="第"+(i+1)+"题：  "+test.getQnA(i).getQuestion();
            	 addParagraph(document, testSTr, false, false, "000000");
            	QuestionAndAnswer q=test.getQnA(i);
            	List<FillPart> p=q.getAnswer();
            	int cnt=1;
            	for(FillPart f:p) {
            		addParagraph(document,cnt +")"+f.getText(),
            				false, false, "000000");
            		cnt++;
            	}
            }

            try (FileOutputStream out = new FileOutputStream(wordPath)) {
                document.write(out);
                System.out.println("Word 文档生成成功: " + wordPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}

}
