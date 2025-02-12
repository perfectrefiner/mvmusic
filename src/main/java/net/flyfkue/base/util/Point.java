package net.flyfkue.base.util;

public class Point {
	public Point(int py, int px) {
		x = px;
		y = py;
	}

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	public int x;
	public int y;
}