package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day06 {
	static Pattern p = Pattern.compile("");
	public static void main(String[] args) {
		File input = new File("src/main/resources/day06.txt");
		ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line;
			ArrayList<String> group = new ArrayList<String>();
			while ((line = br.readLine()) != null) {
				if(line.equals("")) {
					groups.add(group);
					group = new ArrayList<String>();
				}
				else {
					group.add(line);
				}
			}
			groups.add(group);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		for(ArrayList<String> g : groups) {
			TreeSet<Character> set = new TreeSet<Character>();
			TreeSet<Character> remove = new TreeSet<Character>();
			for(char c : g.get(0).toCharArray()) {
				set.add(c);
			}
			for(int i = 1; i < g.size(); i++) {
				for(Character ch : set) {
					boolean found = false;
					for(char c : g.get(i).toCharArray()) {
						if(ch.charValue() == c) {
							found = true;
							break;
						}
					}
					if(!found) {
						remove.add(ch);
					}
				}
			}
			count += set.size() - remove.size();
		}
		System.out.println(count);
	}
}
