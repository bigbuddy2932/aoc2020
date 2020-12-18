package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;

public class Day01 {

	public static void main(String[] args) {
		File input = new File("src/main/resources/day01.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			Set<Integer> nums = new HashSet<Integer>();
			String line; 
			while ((line = br.readLine()) != null)
				nums.add(Integer.parseInt(line));
			for (Integer i1 : nums) 
				for(Integer i2 : nums)
					for(Integer i3 : nums)
						if(i1.intValue() + i2.intValue() + i3.intValue() == 2020) {
							System.out.println(i1.intValue() * i2.intValue() * i3.intValue());
							return;
						}
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
