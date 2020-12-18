package net.benjaneer.aoc2020;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.*;

public class Day13 {
	public static class Bus {
		public int id;
		public int offset;
		public Bus(int i, int o) {
			id = i;
			offset = o;
		}
	}
	public static class SearchThread extends Thread {
		public volatile BigInteger t;
		public volatile BigInteger add;
		private final Bus max;
		private final ArrayList<Bus> list;
		public volatile boolean done = false;
		public volatile boolean bad = false;
		public SearchThread(String s, ArrayList<Bus> list) {
			t = new BigInteger(s);
			this.list = list;
			Bus lmax = new Bus(0, 0);
			for(Bus b : list) {
				if(b.id > lmax.id) {
					lmax = b;
				}
			}
			max = lmax;
			t = t.add(new BigInteger("" + (max.id - max.offset)));
			add = new BigInteger("" + (max.id));
		}
		public void run() {
			boolean search = true;
			while(search) {
				if(t.mod(new BigInteger("" + list.get(0).id)).intValue() != 0) {
					t = t.add(add);
					continue;
				}
				search = false;
				for(Bus b : list) {
					int away = t.mod(new BigInteger(b.id + "")).intValue();
					if(away != 0) {
						away = b.id - away;
					}
					if(away != b.offset) {
						search = true;
						continue;
					}
				}
				t = t.add(add);
			}
			t = t.subtract(add);
			if(!bad) {
				System.out.println(t);
			}
			done = true;
		}
	}
	public static void main(String[] args) {
		Pattern p = Pattern.compile("([0-9]+|x)(,|$)");
		File input = new File("src/main/resources/day13.txt");
		ArrayList<Bus> list = new ArrayList<Bus>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line = br.readLine(); 
			line = br.readLine(); 
			Matcher m = p.matcher(line);
			int offset = 0;
			while(m.find()) {
				if(!m.group(1).equals("x")) {
					list.add(new Bus(Integer.parseInt(m.group(1)), offset));
				}
				offset++;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<SearchThread> tl = new ArrayList<SearchThread>();
		for(int i = 0; i < 10; i++) {
			tl.add(new SearchThread( i + "50000000000000", list));
			tl.add(new SearchThread( i + "00000000000000", list));
		}
		for(SearchThread t : tl) {
			t.start();
		}
		
	}
	/*
	 * Wrong:
	 * 17
	 * 
	 * 
	 */

}
