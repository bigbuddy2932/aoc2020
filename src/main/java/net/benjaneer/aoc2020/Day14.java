package net.benjaneer.aoc2020;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.*;

public class Day14 {
	public static void sort(List<Memory> list) {
	    sort(list, 0, list.size() - 1);
	}

	public static void sort(List<Memory> list, int from, int to) {
	    if (from < to) {
	        int pivot = from;
	        int left = from + 1;
	        int right = to;
	        long pivotValue = list.get(pivot).val;
	        while (left <= right) {
	            // left <= to -> limit protection
	            while (left <= to && pivotValue >= list.get(left).val) {
	                left++;
	            }
	            // right > from -> limit protection
	            while (right > from && pivotValue < list.get(right).val) {
	                right--;
	            }
	            if (left < right) {
	                Collections.swap(list, left, right);
	            }
	        }
	        Collections.swap(list, pivot, left - 1);
	        sort(list, from, right - 1); // <-- pivot was wrong!
	        sort(list, right + 1, to);   // <-- pivot was wrong!
	    }
	}
	public static class Mask extends Cmd{
		public MaskState[] mask = new MaskState[36];
		public Mask(Matcher m) {
			char[] states = m.group(5).toCharArray();
			for(int i = 0; i < states.length; i++) {
				int inv = mask.length - i - 1;
				switch(states[i]) {
				case '1':
					mask[inv] = MaskState.ONE;
					break;
				case '0':
					mask[inv] = MaskState.ZERO;
					break;
				default:
					mask[inv] = MaskState.NONE;
				}
			}
		}
		public String toString() {
			String o = "";
			for(int i = 0; i < mask.length; i++) {
				switch(mask[i]) {
					case ZERO:
						o = "0" + o;
						break;
					case ONE:
						o = "1" + o;
						break;
					case NONE:
						o = "X" + o;
						break;
				}
			}
			return o;
		}
	}
	public static abstract class Cmd{}
	public static enum MaskState{
		ONE, ZERO, NONE
	}
	public static byte getBit(long ID, long position){
	   return (byte) ((ID >> position) & 1);
	}
	public static long setBit(long val, long i, boolean s){
		if(s) {
			if(getBit(val, i) != 1) {
				val += 1L << i;
			}
		}
		else {
			if(getBit(val, i) != 0) {
				val -= 1L << i;
			}
		}
		return val;
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<Memory> permutate(ArrayList<Long> z, Memory m){
		if(z.size() == 0) {
			ArrayList<Memory> o = new ArrayList<Memory>();
			o.add(m);
			return o;
		}
		ArrayList<Memory> o = new ArrayList<Memory>();
		long p = z.get(0);
		ArrayList<Long> nz = (ArrayList<Long>) z.clone();
		nz.remove(0);
		{
			Memory m1 = m.clone();
			m1.pos = setBit(m1.pos, p, false);
			o.addAll(permutate(nz, m1));
		}
		{
			Memory m2 = m.clone();
			m2.pos = setBit(m2.pos, p, true);
			o.addAll(permutate(nz, m2));
		}
		return o;
	}
	public static class Memory extends Cmd{
		public String toString() {
			String o = "";
			for(int i = 0; i < 36; i++) {
				if(getBit(pos, i) == 1) {
					o = "1" + o;
				}
				else {
					o = "0" + o;
				}
			}
			return o;
		}
		public long pos;
		public long val;
		public Memory(Matcher m) {
			this.pos = Long.parseLong(m.group(3));
			this.val = Long.parseLong(m.group(5));
		}
		private Memory(long pos, long val) {
			this.pos = pos;
			this.val = val;
		}
		public ArrayList<Memory> addMask(Mask mask) {
			ArrayList<Memory> o = new ArrayList<Memory>();
			MaskState[] m = mask.mask;
			ArrayList<Long> z = new ArrayList<Long>();
			for(long i = 0; i < m.length; i++) {
				switch(m[(int) i]) {
				case NONE:
					z.add(i);
					break;
				case ONE:
					if(getBit(pos, i) != 1) {
						pos += 1L << i;
					}
					break;
				case ZERO:
					break;
				}
			}
			o = permutate(z, this);
			return o;
		}
		public Memory clone() {
			return new Memory(pos, val);
		}
	}
	public static void main(String[] args) {
		Pattern p = Pattern.compile("((mem\\[([0-9]+)\\])|(mask)) = ([0-9X]+)");
		File input = new File("src/main/resources/day14.txt");
		ArrayList<Matcher> list = new ArrayList<Matcher>();
		BigInteger count = BigInteger.ZERO;
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
		TreeMap<Long, Memory> cmds = new TreeMap<Long, Memory>();
		Mask mask = null;
		for(Matcher m : list) {
			if(m.group(4) == null) {
				Memory mem = new Memory(m);
				ArrayList<Memory> addrs = mem.addMask(mask);
				for(Memory i : addrs) {
					cmds.put(i.pos, i);
				}
			}
			else {
				mask = new Mask(m);
			}
		}
		for(Memory m : cmds.values()) {
			BigInteger b = new BigInteger("" + m.val);
			count = count.add(b);
		}
		System.out.println(count);
	}
	/*
	 * Wrong:
	 * 1881908726
	 * 13496669152158
	 */

}
