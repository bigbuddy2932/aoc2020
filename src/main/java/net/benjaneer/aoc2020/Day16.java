package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day16 {
	public static class Range{
		public int l, u;
		public Range(int l, int u) {
			this.l = l;
			this.u = u;
		}
		public boolean inRange(int i) {
			return i >= l  && i <= u;
		}
	}
	public static class Field implements Comparable<Field>{
		public String name;
		public Range r1, r2;
		public Field(Matcher m) {
			name = m.group(1);
			r1 = new Range(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
			r2 = new Range(Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
		}
		@Override
		public int compareTo(Field o) {
			return name.compareTo(o.name);
		}
		@Override
		public String toString() {
			return name;
		}
	}
	public static boolean nullCheck(Object[] arr) {
		for(Object o : arr) {
			if(o == null)
				return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Pattern p = Pattern.compile("([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");
		File input = new File("src/main/resources/day16.txt");
		ArrayList<Field> list = new ArrayList<Field>();
		ArrayList<Integer> mine = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> others = new ArrayList<ArrayList<Integer>>();
		long count = 1;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			byte stage = 0;
			while ((line = br.readLine()) != null) {
				if(line.equals("")) {
					stage++;
					continue;
				}
				switch(stage) {
					case 0:
						Matcher m = p.matcher(line);
						if(m.matches()) {
							list.add(new Field(m));
						}
						break;
					case 1:
						if(line.equals("your ticket:")) {
							continue;
						}
						for(String s : line.split(",")) {
							mine.add(Integer.parseInt(s));
						}
						break;
					case 2:
						if(line.equals("nearby tickets:")) {
							continue;
						}
						ArrayList<Integer> other = new ArrayList<Integer>();
						for(String s : line.split(",")) {
							other.add(Integer.parseInt(s));
						}
						others.add(other);
						break;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Integer> bads = new ArrayList<Integer>();
		int index = 0;
		for(ArrayList<Integer> tix : others) {
			for(Integer i : tix) {
				boolean bad = true;
				for(Field f : list) {
					if(f.r1.inRange(i)) {
						bad = false;
						break;
					}
					if(f.r2.inRange(i)) {
						bad = false;
						break;
					}
				}
				if(bad) {
					bads.add(index);
					break;
				}
			}
			index++;
		}
		Collections.reverse(bads);
		for(int b : bads) {
			others.remove(b);
		}
		ArrayList<ArrayList<Field>> possibles = new ArrayList<ArrayList<Field>>();
		for(int i = 0; i < mine.size(); i++) {
			possibles.add((ArrayList<Field>) list.clone());
		}
		for(ArrayList<Integer> tix : others) {
			
			for(int i = 0; i < tix.size(); i++) {
				int cmp = tix.get(i);
				List<Field> pos = possibles.get(i);
				for(int f = 0; f < pos.size(); f++) {
					boolean bad = true;
					Field ff = pos.get(f);
					if(ff.r1.inRange(cmp)) {
						bad = false;
						continue;
					}
					else if(ff.r2.inRange(cmp)) {
						bad = false;
						continue;
					}
					if(bad) {
						pos.remove(f);
						f--;
					}
				}
			}
		}
		Field[] truth = new Field[mine.size()];
		while(nullCheck(truth)) {
			for(int f = 0; f < truth.length; f++) {
				ArrayList<Field> pos = possibles.get(f);
				if(pos.size() == 1) {
					truth[f] = pos.get(0);
					for(int ff = 0; ff < possibles.size(); ff++) {
						if(ff != f) {
							ArrayList<Field> poss = possibles.get(ff);
							poss.remove(truth[f]);
						}
					}
				}
			}
		}
		for(int i = 0; i < mine.size(); i++) {
			Field f = truth[i];
			if(f.name.startsWith("departure ")) {
				count *= mine.get(i);
			}
		}
		System.out.println(count);
		/* wrong
		 * 577037097
		 * 
		 */
	}

}
