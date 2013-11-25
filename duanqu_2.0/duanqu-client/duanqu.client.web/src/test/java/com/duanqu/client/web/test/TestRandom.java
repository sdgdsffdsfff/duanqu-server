package com.duanqu.client.web.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestRandom {

	private List<String> letterArray = new ArrayList<String>();
	private List<String> numberArray = new ArrayList<String>();

	private Set<String> resultSet = new HashSet<String>();

	private int i = 0;

	// L，Y，K，D，3，5，9，4
	private void init() {
		letterArray.add("L");
		letterArray.add("Y");
		letterArray.add("K");
		letterArray.add("D");

		numberArray.add("3");
		numberArray.add("5");
		numberArray.add("9");
		numberArray.add("4");
	}

	private List<String> getRandomStringFromArray(List<String> str) {
		List<String> result = new ArrayList<String>();
		while (result.size() < 3) {
			int i = this.getRandomInt(str.size());
			result.add(str.get(i));
			str.remove(i);
		}
		return result;
	}

	private int getRandomInt(int i) {
		Random random = new Random();
		return random.nextInt(i);
	}

	private void sort(List<String> datas, List<String> target) {
		if (target.size() == 6) {
			StringBuffer sb = new StringBuffer();
			for (Object obj : target) {
				sb.append(obj);
			}
			resultSet.add(sb.toString());
			return;
		}
		for (int i = 0; i < datas.size(); i++) {
			List<String> newDatas = new ArrayList<String>(datas);
			List<String> newTarget = new ArrayList<String>(target);
			newTarget.add(newDatas.get(i));
			newDatas.remove(i);
			sort(newDatas, newTarget);
		}
	}

	public static void main(String[] args) {
		TestRandom test = new TestRandom();
		while (test.resultSet.size() < 60100) {
			test.init();
			List<String> old = new ArrayList<String>();
			old.addAll(test.getRandomStringFromArray(test.letterArray));
			old.addAll(test.getRandomStringFromArray(test.numberArray));
			test.sort(old, new ArrayList<String>());
		}
		try{
			File file = new File("/home/tiger/result.txt");
			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			int i = 0;
			for (String pass : test.resultSet) {
				output.write(i++ + "\t" + pass+"\n");
			}
			output.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
