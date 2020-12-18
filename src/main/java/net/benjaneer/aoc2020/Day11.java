package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;

public class Day11 {
	public static boolean changed(ArrayList<char[]> list1, ArrayList<char[]> list2) {
		int x, y;
		x = list1.get(0).length;
		y = list1.size();
		for(int yy = 0; yy < y; yy++) {
			for(int xx = 0; xx < x; xx++) {
				if(list1.get(yy)[xx] != list2.get(yy)[xx]) {
					return false;
				}
			}
		}
		return true;
	}
	public static char occRel(int x, int y, int dx, int dy, ArrayList<char[]> list) {
		try {
			if(list.get(y + dy)[x + dx] == '#') {
				return '#';
			}
		}
		catch(IndexOutOfBoundsException e){
			return 'L';
		}
		return list.get(y + dy)[x + dx];
	}
	public static ArrayList<char[]> cycle(ArrayList<char[]> list){
		int x, y;
		x = list.get(0).length;
		y = list.size();
		ArrayList<char[]> o = new ArrayList<char[]>();
		for(char[] i : list) {
			o.add(i.clone());
		}
		for(int xx = 0; xx < x; xx++) {
			for(int yy = 0; yy < y; yy++) {
				if(list.get(yy)[xx] == '.') {
					continue;
				}
				int c = 0;
				for(int dx = -1; dx < 2; dx++) {
					for(int dy = -1; dy < 2; dy++) {
						int ddx = 0, ddy = 0;
						if(dx == 0 && dy == 0) {
							continue;
						}
						char state = '.';
						while(state == '.') {
							ddx += dx;
							ddy += dy;
							state = occRel(xx, yy, ddx, ddy, list);
						}
						if(state == '#') {
							c++;
						}
					}
				}
				//System.out.println(xx + ":" + yy + ":" + c);
				if(list.get(yy)[xx] == 'L' && c == 0) {
					o.get(yy)[xx] = '#';
				}
				else if(list.get(yy)[xx] == '#' && c >= 5){
					o.get(yy)[xx] = 'L';
				}
			}
		}
		return o;
	}
	public static void main(String[] args) {
		File input = new File("src/main/resources/day11.txt");
		ArrayList<char[]> list1 = new ArrayList<char[]>();
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				char[] arr = line.toCharArray();
				list1.add(arr);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<char[]> list2 = cycle(list1);
		do {
			list1 = list2;
			list2 = cycle(list1);
		} while(!changed(list1, list2));
		int x, y;
		x = list1.get(0).length;
		y = list1.size();
		for(int yy = 0; yy < y; yy++) {
			for(int xx = 0; xx < x; xx++) {
				if(list1.get(yy)[xx] == '#') {
					count++;
				}
				System.out.print(list2.get(yy)[xx]);
			}
			System.out.println();
		}
		System.out.println(count);
	}

}
