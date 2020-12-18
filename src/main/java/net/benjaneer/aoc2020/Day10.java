package net.benjaneer.aoc2020;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Day10 {
	public static BigInteger combinate(ArrayList<Integer> list, int currentPos, TreeMap<Integer, BigInteger> Cache) {
		if(Cache.containsKey(currentPos)) {
			return Cache.get(currentPos);
		}
		if(currentPos >= list.size() - 2) {
			return BigInteger.ONE;
		}
		BigInteger o = BigInteger.ZERO;
		int currentJolt = list.get(currentPos);
		for(int i = currentPos + 1; i < currentPos + 4; i++) {
			if(i < list.size() && list.get(i) - currentJolt < 4) {
				o = o.add(combinate(list, i, Cache));
			}
		}
		Cache.put(currentPos, o);
		return o;
	}
	public static void main(String[] args) {
		File input = new File("src/main/resources/day10.txt");
		ArrayList<Integer> list = new ArrayList<Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				Integer in = Integer.parseInt(line);
				list.add(in);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		list.add(0);
		Collections.sort(list);
		TreeMap<Integer, BigInteger> Cache = new TreeMap<Integer, BigInteger>();
		System.out.println(combinate(list, 0, Cache));
	}

}
