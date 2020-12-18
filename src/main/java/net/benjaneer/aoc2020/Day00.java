package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day00 {

	public static void main(String[] args) {
		Pattern p = Pattern.compile("");
		File input = new File("src/main/resources/day0.txt");
		ArrayList<Matcher> list = new ArrayList<Matcher>();
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				char[] arr = line.toCharArray();
				Matcher m = p.matcher(line);
				if(m.matches()) {
					list.add(m);
				}
				list.add(p.matcher(line));
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(count);
	}

}
