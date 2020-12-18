package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;

public class Day05 {
	public static class Range {
		public int l = 0, u;
		public Range(int u){
			this.u = u;
		}
		public int size() {
			return u - l + 1;
		}
		public void up() {
			l += size()/2;
		}
		public void down() {
			u -= size()/2;
		}
	}
	public static void main(String[] args) {
		File input = new File("src/main/resources/day05.txt");
		TreeSet<Integer> list = new TreeSet<Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				char[] arr = line.toCharArray();
				Range row = new Range(127), col = new Range(7);
				for(int i = 0; i < 7; i++) {
					if(arr[i] == 'F') {
						row.down();
					}
					else {
						row.up();
					}
				}
				for(int i = 7; i < 10; i++) {
					if(arr[i] == 'L') {
						col.down();
					}
					else {
						col.up();
					}
				}
				list.add(row.l * 8 + col.l);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 957; i++) {
			if(list.contains(i) && list.contains(i+2) && !list.contains(i+1)) {
				System.out.println(i);
			}
		}
	}

}
