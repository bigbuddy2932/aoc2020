package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
public class Day15 {
	public static long TARGET = 30000000L;
	public static void main(String[] args) {
		File input = new File("src/main/resources/day15.txt");
		TreeMap<Long, Long> list = new TreeMap<Long, Long>();
		long turn = 1;
		long prev = 0;
		long size = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				String[] s = line.split(",");
				size = s.length;
				for(String sr : s) {
					long c = Long.parseLong(sr);
					prev = c;
					list.put(c, turn);
					turn++;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		long now = 0;
		while(turn < TARGET + 1) {
			if(turn % 1000000 == 0) {
				System.out.println(turn);
			}
			if(list.containsKey(prev) && size + 1 < turn) {
				now = turn - 1 - list.get(prev);
				list.put(prev, turn - 1);
			}
			else if (size + 1 < turn) {
				now = 0;
				list.put(prev, turn - 1);
			}
			prev = now;
			turn++;
		}
		System.out.println(now);
	}

}
