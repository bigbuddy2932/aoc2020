package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;

public class Day09 {

	public static void main(String[] args) {
		int weakness = 167829540;
		File input = new File("src/main/resources/day09.txt");
		ArrayList<Integer> all = new ArrayList<Integer>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				all.add(Integer.parseInt(line));
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < all.size(); i++) {
			for(int j = i + 1; j < all.size(); j++) {
				int sum = 0;
				int max = 0;
				int min = Integer.MAX_VALUE;
				for(int k = i; k < j + 1; k++) {
					sum += all.get(k);
					max = Math.max(max, all.get(k));
					min = Math.min(min, all.get(k));
				}
				if(sum == weakness) {
					System.out.println(max + min);
					return;
				}
			}
		}
	}

	/*
	 * public static void main(String[] args) {
		BigInteger weakness = new BigInteger("167829540");
		File input = new File("src/main/resources/day09.txt");
		ArrayList<BigInteger> previous = new ArrayList<BigInteger>();
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				if(i < 25) {
					previous.add(new BigInteger(line));
					i++;
					continue;
				}
				BigInteger current = new BigInteger(line);
				boolean good = false;
				for(BigInteger x : previous) {
					for(BigInteger y : previous) {
						if(x.add(y).equals(current)) {
							good = true;
							break;
						}
					}
				}
				if(!good) {
					System.out.println(current);
				}
				previous.remove(0);
				previous.add(current);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * Wrong
		 * 83
		 * 30
		 */
	//}
}
