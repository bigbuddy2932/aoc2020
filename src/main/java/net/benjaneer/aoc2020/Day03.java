package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;

public class Day03 {

	public static void main(String[] args) {
		int i = slope(1, 1);
		i *= slope(3, 1);
		i *= slope(5, 1);
		i *= slope(7, 1);
		i *= slope(1, 2);
		System.out.println(i);
	}
	public static int slope(int dx, int dy) {
		File input = new File("src/main/resources/day03.txt");
		int count = 0;
		ArrayList<char[]> map = new ArrayList<char[]>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				map.add(line.toCharArray());
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		int x = dx;
		for(int y = dy; y < map.size(); x+= dx) {
			if(map.get(y)[x % 31] == '#') {
				count++;
			}
			y+=dy;
		}
		return count;
	}

}
