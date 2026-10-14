package com.gamewolf.examtool.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExamUtil {
	
	public static List<Integer> pickByShuffle(int n, int m) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        Collections.shuffle(list);
        return list.subList(0, m);
    }

}
