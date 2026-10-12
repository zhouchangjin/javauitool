package com.gamewolf.examtool.data;

import java.util.ArrayList;
import java.util.List;

public class FillTest {
	
	public List<List<FillPart>> tests;
	
	public FillTest() {
		 tests=new ArrayList<List<FillPart>>();
	}
	
	public void addTest(List<FillPart> t) {
		tests.add(t);
	}
	
	public List<FillPart> getTest(int id) {
		return tests.get(id);
	}
	
	public int getCnt() {
		return tests.size();
	}
	

}
