package net.benjaneer.aoc2020;

import java.io.*;
import java.util.*;

public class Day17 {
	public static class Coord{
		public long x, y, z;
		public Coord(long x, long y, long z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
	}
	/*public static Coord[] getMinMax(TreeMap<Long, TreeMap<Long, TreeSet<Long>>> c) {
		Coord[] o = new Coord[2];
		o[0] = new Coord(Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);//to be max
		o[1] = new Coord(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);//to be min
		for(Long x : c.keySet()) {
			o[0].x = Math.max(x, o[0].x);
			o[1].x = Math.min(x, o[1].x);
		}
		for(TreeMap<Long, TreeSet<Long>> x : c.values()) {
			for(Long y : x.keySet()) {
				o[0].y = Math.max(y, o[0].y);
				o[1].y = Math.min(y, o[1].y);
			}
		}
		return o;
	}*/
	public static void printgame(boolean[][][] c) {
		for(int z = 0; z < ZSIZE; z++) {
			System.out.println("z=" + z);
			for(int y = 0; y < SIZE; y++) {
				for(int x = 0; x < SIZE; x++) {
					if(c[x][y][z]) {
						System.out.print("#");
					}
					else {
						System.out.print(".");
					}
				}
				System.out.println();
			}
		}
	}
	public static boolean checkRel(boolean[][][][] c, int x, int y, int z, int w) {
		if(x >= 0 && x < SIZE) {
			if(y >= 0 && y < SIZE) {
				if(z >= 0 && z < ZSIZE) {
					if(w >= 0 && w < ZSIZE) {
						return c[x][y][z][w];
					}
				}
			}
		}
		return false;
	}
	public static byte getCount(boolean[][][][] c, int x, int y, int z, int w) {
		byte o = 0;
		for(int xi = -1; xi < 2; xi++) {
			for(int yi = -1; yi < 2; yi++) {
				for(int zi = -1; zi < 2; zi++) {
					for(int wi = -1; wi < 2; wi++) {
						if(zi == 0 && yi == 0 && xi == 0 && wi == 0) {
							continue;
						}
						if(checkRel(c, x + xi, y + yi, z + zi, w + wi)) {
							o++;
						}
					}
				}
			}
		}
		return o;
	}
	public static void addCube(TreeMap<Long, TreeMap<Long, TreeMap<Long, Boolean>>> cubes, long x, long y, long z, boolean active) {
		if(cubes.containsKey(x)) {
			TreeMap<Long, TreeMap<Long, Boolean>> yl = cubes.get(x);
			if(yl.containsKey(y)) {
				TreeMap<Long, Boolean> zl = yl.get(y);
				if(!active && zl.containsKey(z)) {
					return;
				}
				zl.put(z, active);
			}
			else {
				TreeMap<Long, Boolean> zl = new TreeMap<Long, Boolean>();
				zl.put(z, active);
				yl.put(y, zl);
			}
		}
		else {
			TreeMap<Long, TreeMap<Long, Boolean>> yl = new TreeMap<Long, TreeMap<Long, Boolean>>();
			TreeMap<Long, Boolean> zl = new TreeMap<Long, Boolean>();
			zl.put(z, active);
			yl.put(y, zl);
			cubes.put(x, yl);
		}
	}
	public static void setCube(TreeMap<Long, TreeMap<Long, TreeMap<Long, Boolean>>> cubes, long x, long y, long z, boolean active) {
		if(cubes.containsKey(x)) {
			TreeMap<Long, TreeMap<Long, Boolean>> yl = cubes.get(x);
			if(yl.containsKey(y)) {
				TreeMap<Long, Boolean> zl = yl.get(y);
				zl.put(z, active);
			}
			else {
				TreeMap<Long, Boolean> zl = new TreeMap<Long, Boolean>();
				zl.put(z, active);
				yl.put(y, zl);
			}
		}
		else {
			TreeMap<Long, TreeMap<Long, Boolean>> yl = new TreeMap<Long, TreeMap<Long, Boolean>>();
			TreeMap<Long, Boolean> zl = new TreeMap<Long, Boolean>();
			zl.put(z, active);
			yl.put(y, zl);
			cubes.put(x, yl);
		}
	}
	public static boolean[][][][] fullClone(boolean[][][][] c){
		boolean[][][][] o = new boolean[SIZE][SIZE][ZSIZE][ZSIZE];
		for(int x = 0; x < c.length; x++) {
			for(int y = 0; y < c[x].length; y++) {
				for(int z = 0; z < c[x][y].length; z++) {
					for(int w = 0; w < c[x][y][z].length; w++)
						o[x][y][z][w] = c[x][y][z][w];
				}
			}
		}
		return o;
	}
	public static final long CYCLES = 6;
	public static final int OFF = 15;
	public static final int SIZE = 30;
	public static final int ZSIZE = 30;
	public static void main(String[] args) {
		boolean[][][][] board = new boolean[SIZE][SIZE][ZSIZE][ZSIZE];
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[x].length; y++) {
				for(int z = 0; z < board[x][y].length; z++) {
					for(int w = 0; w < board[x][y].length; w++)
						board[x][y][z][w] = false;
				}
			}
		}
		File input = new File("src/main/resources/day17.txt");
		ArrayList<char[]> linelist = new ArrayList<char[]>();
		int count = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(input))){
			String line; 
			while ((line = br.readLine()) != null) {
				linelist.add(line.toCharArray());
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		//TreeMap<Long, TreeMap<Long, TreeMap<Long, Boolean>>> cubes = new TreeMap<Long, TreeMap<Long, TreeMap<Long, Boolean>>>();
		for(int y = 0; y < linelist.size(); y++) {
			char[] line = linelist.get(y);
			for(int x = 0; x < line.length; x++) {
				if(line[x] == '#') {
					board[x + OFF][y + OFF][OFF][OFF] = true;
				}
			}
		}
		//printgame(board);
		for(long cycle = 0; cycle < CYCLES; cycle++) {
			{
				boolean[][][][] newboard = fullClone(board);
				for(int x = 0; x < board.length; x++) {
					for(int y = 0; y < board[x].length; y++) {
						for(int z = 0; z < board[x][y].length; z++) {
							for(int w = 0; w < board[x][y][z].length; w++) {
								byte nei = getCount(board, x, y, z, w);
								if(board[x][y][z][w]) {
									if(nei == 2 || nei == 3) {
										newboard[x][y][z][w] = true;
									}
									else {
										newboard[x][y][z][w] = false;
									}
								}
								else {
									if(nei == 3) {
										newboard[x][y][z][w] = true;
									}
									else {
										newboard[x][y][z][w] = false;
									}
								}
							}
						}
					}
				}
				board = newboard; 
			}
			count = 0;
			for(int x = 0; x < board.length; x++) {
				for(int y = 0; y < board[x].length; y++) {
					for(int z = 0; z < board[x][y].length; z++) {
						for(int w = 0; w < board[x][y][z].length; w++) {
							if(board[x][y][z][w]) {
								count++;
							}
						}
					}
				}
			}
			System.out.println(count);
		}
	}
/*
 * Wrong
 * 1992 too low
 */
}
