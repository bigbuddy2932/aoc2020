package net.benjaneer.aoc2020;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.*;

public class Day13b {
	public static class Bus {
		public int id;
		public int offset;
		public Bus(int i, int o) {
			id = i;
			offset = o;
		}
	}
	public static void main(String[] args) {
		Pattern p = Pattern.compile("([0-9]+|x)(,|$)");
		File input = new File("src/main/resources/day13.txt");
		ArrayList<Bus> list = new ArrayList<Bus>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line = br.readLine(); 
			line = br.readLine(); 
			Matcher m = p.matcher(line);
			int offset = 0;
			while(m.find()) {
				if(!m.group(1).equals("x")) {
					list.add(new Bus(Integer.parseInt(m.group(1)), offset));
				}
				offset++;
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		BigInteger ts = BigInteger.ZERO;
		BigInteger ntot = BigInteger.ONE;
		for(Bus b : list) {
			BigInteger bmod = new BigInteger("" + b.id);
			BigInteger bid = new BigInteger("" + b.offset);
			BigInteger inv = ntot.modInverse(bmod);
			BigInteger res = bmod.subtract(ts.add(bid).mod(bmod)).mod(bmod);
			BigInteger n = inv.multiply(res).mod(bmod);
			ts = ts.add(ntot.multiply(n));
			ntot = ntot.multiply(bmod);
		}
		System.out.print(ts);
	}
	/*
	 * E ans: 754018
	 * R ans: 1106724616194525
	 * 
	 */

}
