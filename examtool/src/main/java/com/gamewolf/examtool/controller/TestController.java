package com.gamewolf.examtool.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gamewolf.examtool.data.DataTool;
import com.gamewolf.examtool.data.ExamUtil;
import com.gamewolf.examtool.data.FillPart;
import com.gamewolf.examtool.data.FillTest;
import com.gamewolf.examtool.data.QuestionAndAnswer;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TestController {
	
	public static String TEST_FILLTEST="FILL";
	
	public static String TEST_ANSWERTEST="QNA";
	
	IKAnalyzer an;
	
	FillTest test;
	
	FillTest testDb;

	String path=null;
	
	@FXML
	TextFlow testText;
	
	@FXML
	VBox answPane;
	
	@FXML
	Button preButton;
	
	@FXML
	Button nextButton;
	
	//int cnt;
	
    //List<FillPart> current;
	
	int currentTestNo;
	
	String currentTestType;
    
    List<TextField> currentTextField;
    
    boolean randMode=true;
    
    int pos=0;

	int score=0;
	int totalScore=0;

	private void loadTest(){
		if(currentTextField==null){
			currentTextField=new ArrayList<TextField>();
		}else{
			currentTextField.clear();
		}

		testText.getChildren().clear();
		pos=0;
		testDb=DataTool.read(path);
		test=new FillTest();
		test.init(testDb);
		//cnt=test.getCnt();
		List<FillPart> t=genTestField();
		genAnswerField(t);
	}
	
	@FXML
	void initialize() {
		an=new IKAnalyzer();
		System.out.println("helloworld");
		if(path==null){
			path=getClass().getResource("").getPath()+"题库。txt";
			loadTest();
		}

	}
	
	private int randFillTestNo() {
		int cnt=test.getCnt();
		int rnd=new Random().nextInt(cnt);
		
		return rnd;
	}

	private List<FillPart> genTestField() {
		int tn=randFillTestNo();
		List<FillPart> t=test.getTest(tn);
		//current=t;
		currentTestType=TEST_FILLTEST;
		currentTestNo=tn;
		for(FillPart f:t) {
			if(f.isFillPart()) {
				Text blank=new Text("____________");
				testText.getChildren().add(blank);
			}else {
				Text text=new Text(f.getText());
				//text.setStyle("-fx-fill: #87CEEB");
				testText.getChildren().add(text);
			}

		}
		return t;
	}

	void genAnswerField(List<FillPart> t) {
		answPane.getChildren().clear();
		int cnt=0;
		for(FillPart f:t) {
			if(f.isFillPart()) {
				cnt++;
				HBox hbox=new HBox();
				hbox.getChildren().add(new Label(cnt+":"));
				TextField tf=new TextField();
				currentTextField.add(tf);
				hbox.getChildren().add(tf);
				hbox.setPadding(new Insets(10, 0, 0, 0));
				answPane.getChildren().add(hbox);
			}
		}
		
	}
	
	@FXML
	void nextTest() {
		System.out.println("next");
		
		checkAnswer(currentTestNo,currentTestType);
		
		if(randMode) {
			preButton.setDisable(true);
			float rndTestType=new Random().nextFloat();
			if(rndTestType<0.6 || test.getQnATestCnt()==0) {
				int rnd=randFillTestNo();
				choose(rnd,TEST_FILLTEST);
			}else {
				int rnd=randQnATestNo();
				choose(rnd,TEST_ANSWERTEST);
			}
		
		}else {
			preButton.setDisable(false);
			pos++;
			int totalFill=test.getCnt();
			int ansPos=pos-totalFill;
			int totalQa=test.getQnATestCnt();
			int totalTest=totalFill+totalQa;
			
			System.out.println("第"+(pos+1)+"题，总共"+totalFill+"填空"+totalQa+"简答题");
			
			if(pos<totalTest) {
				
				if(ansPos>=0) {
					choose(ansPos,TEST_ANSWERTEST);
				}else {
					choose(pos,TEST_FILLTEST);
				}
				
			}else {
				nextButton.setDisable(true);
			}
		}
		
	}
	


	private int randQnATestNo() {
		int cnt=test.getQnATestCnt();
		return new Random().nextInt(cnt);
	}
	
	private void checkFillAns(List<FillPart> test) {
		List<String> correctAnswer=new ArrayList<String>();
		for(FillPart f:test) {
			if(f.isFillPart()) {
				correctAnswer.add(f.getText());
			}
		}
		String or=test.stream().map(e->{
			if(e.isFillPart()){
				return "\033[32m"+e.getText();
			}else{
				return "\033[36m"+e.getText();
			}
		}).collect(Collectors.joining(""));
		System.out.println("\033[36m"+or);
		for(int i=0;i<currentTextField.size();i++) {
			String answer=currentTextField.get(i).getText();
			System.out.print(answer+":"+correctAnswer.get(i));
			System.out.print(answer.equals(correctAnswer.get(i))?"\033[32m 正确":"\033[31m 错误");
			System.out.println("\033[36m");
		}
	}

	private void checkAnswer(int curTestNo,String type) {
		if(type.equals(TEST_FILLTEST)) {
			List<FillPart> c=test.getTest(curTestNo);
			checkFillAns(c);
		}else if(type.equals(TEST_ANSWERTEST)) {
			QuestionAndAnswer qna=test.getQnA(curTestNo);
			checkQNAAns(qna.getAnswer());
		}
		
		
	}

	private void checkQNAAns(List<FillPart> current2) {
		for(int i=0;i<currentTextField.size();i++) {
			String myAns=currentTextField.get(i).getText();
			double sim=compare(myAns,current2.get(i).getText());
			System.out.println("\033[36m"+current2.get(i).getText()+" 与我的答案\033[32m"+myAns+"\033[36m 对比相似度"+sim);
			
		}
	}
	
	List<String> tokenList(String text){
		List<String> tlist=new ArrayList();
		try (TokenStream ts=an.tokenStream("", text)){
			
			ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			while(ts.incrementToken()) {
				CharTermAttribute t=ts.getAttribute(CharTermAttribute.class);
    			String token=t.toString();
				//System.out.println(token);
    			if(token!=null && !token.isEmpty()) {
    				tlist.add(token);
    			}

			}
			ts.end();
		}catch(IOException e) {
			
		}
		return tlist;
	}

	private double compare(String ans, String correctAnsw)  {

		List<String> can=tokenList(correctAnsw);
		List<String> myan=tokenList(ans);
		
		double similarity=sim(myan,can);
		return similarity;
	}
	
	private Map<String,Integer> toMap(List<String> tlist){
		Map<String,Integer> m=new TreeMap<String, Integer>();
		for(String en:tlist) {
			if(m.containsKey(en)) {
				Integer c=m.get(en);
				m.put(en, c+1);
			}else {
				m.put(en, 1);
			}
		}
		return m;
	}

	private double sim(List<String> left, List<String> right) {
		Map<String,Integer> leftText=toMap(left);
		Map<String,Integer> rightText=toMap(right);
		
		Set<String> allKey=new TreeSet<String>();
		allKey.addAll(left);
		allKey.addAll(right);
		int sumAB=0;
		int sumA=0;
		int sumB=0;
		for(String key:allKey) {
			
			int leftCnt=leftText.containsKey(key)?leftText.get(key):0;
			int rightCnt=rightText.containsKey(key)?rightText.get(key):0;
			sumAB+=leftCnt*rightCnt;
			sumA+=leftCnt*leftCnt;
			sumB+=rightCnt*rightCnt;
		}
		if(sumA==0 || sumB==0) {
			return 0;
		}
		double sim=1.0f*sumAB/Math.sqrt(sumA)/Math.sqrt(sumB);
		return sim;
	}

	@FXML
	void preTest() {
		System.out.println("previous");
		int totalFill=test.getCnt();
		int ansPos=pos-totalFill;
		if(!randMode){
			if(pos>0){
				pos--;
				if(ansPos>=0) {
					choose(ansPos,TEST_ANSWERTEST);
				}else {
					choose(pos,TEST_FILLTEST);
				}
				
			}
			if(pos==0) {
				preButton.setDisable(true);
			}else {
				preButton.setDisable(false);
			}
		}
	}
	
	public void chooseFill(int testNo) {

		List<FillPart> t=test.getTest(testNo);
		for(FillPart f:t) {
			if(f.isFillPart()) {
				testText.getChildren().add(new Text("____________"));
			}else {
				testText.getChildren().add(new Text(f.getText()));
			}
			
		}
		
		genAnswerField(t);
	}

	public void choose(int testNo,String testType) {
		currentTestNo=testNo;
		currentTestType=testType;
		
		currentTextField.clear();
		testText.getChildren().clear();
		if(testType.equals(TEST_FILLTEST)) {
			chooseFill(testNo);
		}else if(testType.equals(TEST_ANSWERTEST)) {
			chooseQnA(testNo);
		}
	}

	private void chooseQnA(int testNo) {
		QuestionAndAnswer q=test.getQnA(testNo);
		testText.getChildren().add(new Text(q.getQuestion()));
		testText.getChildren().add(new Text("请回答：____"));
		genAnswerField(q.getAnswer());
	}

	public void examGen() {
		randMode=false;
		test.clear();
		//生成考试
		
		int n=testDb.getCnt();
		int m=n>10?10:n;
		List<Integer> testNos=ExamUtil.pickByShuffle(n, m);
		for(Integer i:testNos) {
			var t=testDb.getTest(i);
			test.addTest(t);
		}
		
		int qaCnt=testDb.getQnATestCnt();
		if(qaCnt>0) {
			int qAchoose=qaCnt>3?3:qaCnt;
			List<Integer> qanos=ExamUtil.pickByShuffle(qaCnt, qAchoose);
			for(Integer i:qanos) {
				var t=testDb.getQnA(i);
				test.addQnA(t);
			}
		}
		
		pos=0;
		System.out.println("第1题");
		choose(0,TEST_FILLTEST);
		SimpleDateFormat sf=new SimpleDateFormat("MM_dd_HH_mm");
		String testName="考试题"+sf.format(new Date());
		ExamUtil.genWord(test, "d:/",testName);
		ExamUtil.genAnswWord(test,"d:/",testName+"_答案");
	}

	public void loadFile(String path) {
		this.path=path;

		loadTest();
	}
}
