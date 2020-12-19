package net.benjaneer.aoc2020;

import java.io.*;
import java.util.regex.*;

public class Day18 {
	public static final Pattern p1 = Pattern.compile("([0-9]+) ([+]) ([0-9]+)");
	public static final Pattern p2 = Pattern.compile("([0-9]+) ([*]) ([0-9]+)");
	public static long eval(String ss) {
		char[] s = ss.toCharArray();
		int openpos = Integer.MIN_VALUE;
		int closepos = Integer.MIN_VALUE;
		for(int i = 0; i < s.length; i++) {
			if(s[i] == '(') {
				openpos = i;
			}
			else if(s[i] == ')') {
				closepos = i;
				break;
			}
		}
		if(openpos == Integer.MIN_VALUE) {
			boolean matches = true;
			while(matches) {
				Matcher m = p1.matcher(ss);
				matches = m.find();
				if(matches) {
					long i1 = Long.parseLong(m.group(1));
					long i2 = Long.parseLong(m.group(3));
					char op = m.group(2).charAt(0);
					if(op == '+') {
						ss = ss.replaceFirst("([0-9]+) ([+]) ([0-9]+)", (i1 + i2) + "");
					}
					else {
						ss = ss.replaceFirst("([0-9]+) ([+]) ([0-9]+)", (i1 * i2) + "");
					}
				}
			}
			matches = true;
			while(matches) {
				Matcher m = p2.matcher(ss);
				matches = m.find();
				if(matches) {
					long i1 = Long.parseLong(m.group(1));
					long i2 = Long.parseLong(m.group(3));
					char op = m.group(2).charAt(0);
					if(op == '+') {
						ss = ss.replaceFirst("([0-9]+) ([*]) ([0-9]+)", (i1 + i2) + "");
					}
					else {
						ss = ss.replaceFirst("([0-9]+) ([*]) ([0-9]+)", (i1 * i2) + "");
					}
				}
			}
			return Long.parseLong(ss);
		}
		String sub = ss.substring(openpos + 1, closepos);
		sub = eval(sub) + "";
		String p1 = ss.substring(0, openpos);
		String p2 = ss.substring(closepos + 1);
		String p3 = p1 + sub + p2;
		return eval(p3);
	}
	public static void main(String[] args) {
		File input = new File("src/main/resources/day18.txt");
		long count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				count += eval(line);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}

}
