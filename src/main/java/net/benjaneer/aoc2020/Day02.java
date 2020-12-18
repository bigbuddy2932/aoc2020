package net.benjaneer.aoc2020;

import java.io.*;
import java.util.regex.*;

public class Day02 {

	public static void main(String[] args) {
		File input = new File("src/main/resources/day02.txt");
		int ans = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				Pattern p = Pattern.compile("([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)");
				Matcher m = p.matcher(line);
				if(m.matches()) {
					int r1 = Integer.parseInt(m.group(1));
					int r2 = Integer.parseInt(m.group(2));
					char target = m.group(3).charAt(0);
					char[] pass = m.group(4).toCharArray();
					if(pass[r1 - 1] == target ^ pass[r2 - 1] == target) {
						ans++;
					}
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(ans);
	}

}
