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
	
	public static void genWord(FillTest test,String wordPath) {
		XWPFDocument document = new XWPFDocument();

        try {
            // 2. 添加标题
            addTitle(document, "测试题", 1);
            for(int i=0;i<test.getCnt();i++) {
            	String testSTr="第"+(i+1)+"题：  "+test.toTestString(i);

                addParagraph(document, testSTr, false, false, "000000");
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

}
