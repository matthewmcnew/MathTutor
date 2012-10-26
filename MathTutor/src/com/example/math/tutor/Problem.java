package com.example.math.tutor;

public class Problem {
	private int x;
	private int y;
	private int sol;
	private char op;
	private boolean solved;
	
	public Problem (int _x, int _y, int _sol, char _op) {
		x = _x; y = _y; sol = _sol; op = _op;
	}

	public boolean isSolved() {
		return solved;
	}
	
	public int getSol() {
		return sol;
	}
	
	public void setSolved(boolean solved) {
		this.solved = solved;
	}
	
	public String toString() {
		if(solved)
			return "" + x + op + y + " = " + sol;
		else
			return "" + x + op + y;
	}
}
