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
	
	public void clear() {
		tests.clear();
	}
	
	public void init(List<List<FillPart>> all) {
		tests.addAll(all);
	}
	
	public void init(FillTest other) {
		init(other.getAll());
	}
	
	public List<List<FillPart>> getAll(){
		return tests;
	}
	

}
