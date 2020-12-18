package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day12 {
	public static enum Dir{
		N, S, E, W, R, L, F;
	}
	public static void main(String[] args) {
		Pattern p = Pattern.compile("([NSEWLRF])([0-9]+)");
		File input = new File("src/main/resources/day12.txt");
		ArrayList<Matcher> list = new ArrayList<Matcher>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.matches())
					list.add(m);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		int x = 0, y = 0;
		int wx = 10, wy = 1;
		for(Matcher m : list) {
			Dir d = Dir.valueOf(m.group(1));
			int mag = Integer.parseInt(m.group(2));
			switch(d) {
			case N:
				wy += mag;
				break;
			case S:
				wy -= mag;
				break;
			case E:
				wx += mag;
				break;
			case W:
				wx -= mag;
				break;
			case L:
				for(int i = 0; i < mag / 90; i++) {
					int tmp = wx;
					wx = -wy;
					wy = tmp;
				}
				break;
			case R:
				for(int i = 0; i < mag / 90; i++) {
					int tmp = wx;
					wx = wy;
					wy = -tmp;
				}
				break;
			default:
				for(int i = 0; i < mag; i++) {
					x += wx;
					y += wy;
				}
				break;
			}
		}
		System.out.println(Math.abs(x) + Math.abs(y));
	}
	/*
	 * Wrong:
	 * 35288
	 * 91436
	 * 28318
	 */
}
