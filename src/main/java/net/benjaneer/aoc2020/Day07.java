package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.*;

public class Day07 {
	public static class Bag {
		public String color = "";
		public HashMap<Bag, Integer> contains = new HashMap<Bag, Integer>();
		public Bag() {}
		public Bag(String c) {color = c;}
		public boolean eventually(String c) {
			if(color.equals(c)) {
				return true;
			}
			if(contains.size() == 0) {
				return false;
			}
			for(Bag b : contains.keySet()) {
				if(b.eventually(c)) {
					return true;
				}
			}
			return false;
		}
		public int contains() {
			if(contains.size() == 0) {
				return 0;
			}
			int o = 0;
			for(Entry<Bag, Integer> e : contains.entrySet()) {
				o += (e.getValue().intValue());
				o += (e.getValue().intValue() * e.getKey().contains());
			}
			return o;
		}
	}
	public static final String base = "shiny gold";
	public static final Bag baseBag = new Bag(base);
	public static final Pattern p1 = Pattern.compile("([a-z ]+) bags contain ([a-z ,0-9]+)\\.");
	public static final Pattern p2 = Pattern.compile("(([0-9]) ([a-z ]+)) bags?|(no other) bags");
	public static void main(String[] args) {
		File input = new File("src/main/resources/day07.txt");
		ArrayList<Bag> list = new ArrayList<Bag>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			ArrayList<Matcher> m1s = new ArrayList<Matcher>();
			while ((line = br.readLine()) != null) {
				Matcher m1 = p1.matcher(line);
				if(m1.matches()) {
					m1s.add(m1);
				}
			}
			int index = 0;
			while(m1s.size() > 0) {
				Matcher m1 = m1s.get(index);
				Bag b = new Bag();
				b.color = m1.group(1);
				Matcher m2 = p2.matcher(m1.group(2));
				boolean valid = true;
				while(m2.find()) {
					if(m2.group(4) != null) {
						break;
					}
					else {
						boolean found = false;
						for(Bag x : list) {
							if(x.color.equals(m2.group(3))) {
								b.contains.put(x, Integer.parseInt(m2.group(2)));
								found = true;
								break;
							}
						}
						if(!found) {
							valid = false;
							break;
						}
					}
				}
				if(valid) {
					list.add(b);
					m1s.remove(index);
					index--;
				}
				index++;
				if(index >= m1s.size()) {
					index = 0;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		for(Bag b : list) {
			if(b.color.equals(base)) {
				System.out.println(b.contains());
			}
		}
	}
	/*
	 * Wrong
	 * 118
	 * 119
	 * 123
	 * 124
	 * 125
	 */
}
