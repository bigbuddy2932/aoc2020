package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day08 {
	public static enum Inst{
		acc,
		jmp,
		nop
	}
	public static class Instruction {
		public Inst i;
		public int val;
		public boolean ran = false;
		public Instruction(Inst i, int val) {
			this.i = i;
			this.val = val;
		}
	}
	public static void main(String[] args) {
		Pattern p = Pattern.compile("(acc|jmp|nop) (\\+|-)([0-9]+)");
		File input = new File("src/main/resources/day08.txt");
		ArrayList<Instruction> il = new ArrayList<Instruction>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.matches()) {
					Inst inst = Inst.valueOf(m.group(1));
					int val = Integer.parseInt(m.group(3));
					if(m.group(2).equals("-")) {
						val *= -1;
					}
					il.add(new Instruction(inst, val));
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		int acc = 0;
		int pos = 0;
		Instruction current = il.get(pos);
		for(int i = 0; i < 619; i++) {
			System.out.println(i);
			Instruction corr = il.get(i);
			switch(corr.i) {
				case acc:
					continue;
				case jmp:
					corr.i = Inst.nop;
					break;
				case nop:
					corr.i = Inst.jmp;
					break;
			}
			il.set(i, corr);
			pos = 0;
			acc = 0;
			current = il.get(pos);
			while(!current.ran) {
				current.ran = true;
				switch(current.i) {
				case acc:
					acc += current.val;
					pos++;
					break;
				case jmp:
					pos += current.val;
					break;
				case nop:
					pos++;
					break;
				}
				if(pos >= 619) {
					break;
				}
				current = il.get(pos);
			}
			if(pos >= 619) {
				System.out.println(acc);
				return;
			}
			switch(corr.i) {
				case acc:
					continue;
				case jmp:
					corr.i = Inst.nop;
					break;
				case nop:
					corr.i = Inst.jmp;
					break;
			}
			il.set(i, corr);
			for(Instruction in : il) {
				in.ran = false;
			}
		}
	}
	/*
	 * Wrong
	 * 
	 */

}
