package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day04 {

	public static class PP{
		public boolean[] list = new boolean[8];
		public String inspect = "";
		public PP() {
			for(int i = 0; i < 8; i++) {
				list[i] = false;
			}
		}
		public void addStr(String in) {
			Pattern p = Pattern.compile("([a-z]{3}):(#?)([a-z0-9]+(cm|in)?)( |$)");
			Matcher m = p.matcher(in);
			inspect += in + "#";
			while(m.find()) {
				String f = m.group(1);
				if(f.equals("byr")) {
					try {
						int i = Integer.parseInt(m.group(3));
						if(i >= 1920 && i <=2002) {
							list[0] = true;
						}
					}
					catch(NumberFormatException e) {
						
					}
				}
				else if(f.equals("iyr")) {
					try {
						int i = Integer.parseInt(m.group(3));
						if(i >= 2010 && i <=2020) {
							list[1] = true;
						}
					}
					catch(NumberFormatException e) {
						
					}
				}
				else if(f.equals("eyr")) {
					try {
						int i = Integer.parseInt(m.group(3));
						if(i >= 2020 && i <=2030) {
							list[2] = true;
						}
					}
					catch(NumberFormatException e) {
						
					}
				}
				else if(f.equals("hgt")) {
					boolean good = true;
					try {
						int i = Integer.parseInt(m.group(3).substring(0, m.group(3).length() - 2));
						String unit = m.group(3).substring(m.group(3).length() - 2);
						if(unit.equals("cm")) {
							if(i < 150 || i > 193) {
								good = false;
							}
						}
						else if(unit.equals("in")) {
							if(i < 59 || i > 76) {
								good = false;
							}
						}
						else {
							good = false;
						}
					}
					catch(NumberFormatException e) {
						good = false;
					}
					if(good) {
						list[3] = true;
					}
				}
				else if(f.equals("hcl")) {
					if(m.group(2).equals("#") && m.group(3).length() == 6) {
						char[] sir = m.group(3).toCharArray();
						boolean good = true;
						for(char sin : sir) {
							if((sin >= '0' && sin <= '9') || (sin >= 'a' && sin <= 'f')) {
								continue;
							}
							else {
								good = false;
							}
						}
						if(good) {
							list[4] = true;
						}
					}
				}
				else if(f.equals("pid")) {
					if(m.group(3).length() == 9) {
						char[] sir = m.group(3).toCharArray();
						boolean good = true;
						for(char sin : sir) {
							if(sin < '0' || sin > '9') {
								good = false;
								break;
							}
						}
						if(good) {
							list[5] = true;
						}
					}
				}
				else if(f.equals("ecl")) {
					String cmp = m.group(3);
					if(cmp.equals("amb") || cmp.equals("blu") || cmp.equals("brn") || cmp.equals("gry") || cmp.equals("grn") || cmp.equals("hzl") || cmp.equals("oth")) {
						list[6] = true;
					}
				}
				else if(f.equals("cid")) {
					list[7] = true;
				}
			}
		}
		public boolean isValid() {
			for(int i = 0; i < 7; i++) {
				if(!list[i]) {
					return false;
				}
			}
			return true;
		}
	}
	public static void main(String[] args) {
		File input = new File("src/main/resources/day04.txt");
		int count = 0;
		ArrayList<PP> list = new ArrayList<PP>();
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line;
			PP c = new PP();
			while ((line = br.readLine()) != null) {
				if(line.equals("")) {
					list.add(c);
					c = new PP();
				}
				else {
					c.addStr(line);
				}
			}
			list.add(c);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		for(PP i : list) {
			if(i.isValid()) {
				count++;
			}
		}
		System.out.println(count);
	}

}
