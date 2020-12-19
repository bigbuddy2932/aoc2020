package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day19 {
	public static class Rule{
		public int id;
		//?:
		public String regex = "(";
		public Rule(int id, char res) {
			this.id = id;
			regex = "(" + res + ")";
		}
		public Rule(int id, ArrayList<Rule> r1, ArrayList<Rule> r2) {
			this.id = id;
			for(Rule r : r1) {
				regex += r.regex;
			}
			if(r2 != null) {
				regex += "|";
				for(Rule r : r2) {
					regex += r.regex;
				}
			}
			regex += ")";
		}
	}
	public static Pattern p = Pattern.compile("(\\d+): ((\\d+)( (\\d+))?|\"([ab])\")( \\| (\\d+)( (\\d+))?)?");
	public static Pattern gen = Pattern.compile("^0:( \\d)+");
	public static Pattern rz = Pattern.compile(" (\\d+)");
	public static void main(String[] args) {
		File input = new File("src/main/resources/day19.txt");
		ArrayList<Matcher> list = new ArrayList<Matcher>();
		ArrayList<String> ab = new ArrayList<String>();
		ArrayList<Integer> rzero = new ArrayList<Integer>();
		TreeMap<Integer, Rule> rules = new TreeMap<Integer, Rule>();
		int count = 0;
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
					Matcher mg = gen.matcher(line);
					if(mg.find()) {
						Matcher rzm = rz.matcher(line);
						while(rzm.find()) {
							rzero.add(Integer.parseInt(rzm.group(1)));
						}
						continue;
					}
					Matcher m = p.matcher(line);
					if(m.matches()) {
						list.add(m);
						break;
					}
					System.out.println(line);
					break;
				case 1:
					ab.add(line);
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		while(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Matcher m = list.get(i);
				int id = Integer.parseInt(m.group(1));
				if(m.group(6) != null) {
					rules.put(id, new Rule(id, m.group(6).charAt(0)));
					list.remove(i);
					i--;
					continue;
				}
				ArrayList<Rule> r1 = new ArrayList<Rule>(), r2 = null;
				{
					int rn = Integer.parseInt(m.group(3));
					if(rules.containsKey(rn)) {
						r1.add(rules.get(rn));
					}
					else {
						continue;
					}
					if(m.group(5) != null) {
						rn = Integer.parseInt(m.group(5));
						if(rules.containsKey(rn)) {
							r1.add(rules.get(rn));
						}
						else {
							continue;
						}
					}
				}
				if(m.group(7) != null){
					r2 = new ArrayList<Rule>();
					int rn = Integer.parseInt(m.group(8));
					if(rules.containsKey(rn)) {
						r2.add(rules.get(rn));
					}
					else {
						continue;
					}
					if(m.group(10) != null) {
						rn = Integer.parseInt(m.group(10));
						if(rules.containsKey(rn)) {
							r2.add(rules.get(rn));
						}
						else {
							continue;
						}
					}
				}
				list.remove(i);
				rules.put(id, new Rule(id, r1, r2));
			}
		}
		/*
		 * 8: 42 | 42 8
		 * 11: 42 31 | 42 11 31
         * 0: 8 11
		 */
		
		//This has to be the most hacky solution i've ever written for any problem ever
		String r8  = "((" + rules.get(42).regex + ")+)";
		String r11  = "((" + rules.get(42).regex + rules.get(31).regex + ")";
		for(int i = 2; i < 10; i++) {
			String add = "|(";
			for(int j = 0; j < i; j++) {
				add += rules.get(42).regex;
			}
			for(int j = 0; j < i; j++) {
				add += rules.get(31).regex;
			}
			add += ")";
			r11 += add;
		}
		r11 += ")";
		String finalRegex = r8 + r11;
		Pattern finalp = Pattern.compile(finalRegex);
		for(String s : ab) {
			Matcher m = finalp.matcher(s);
			if(m.matches()) {
				count++;
			}
		}
		System.out.println(count);
	}

}
