package br.com.jeffque.checkers.utils;

public class BoardCoord {
	public final int i;
	public final int j;
	public final int sqrModule;
	
	public boolean equiv(BoardCoord another) {
		return i == another.i && j == another.j;
	}
	
	public BoardCoord(int i, int j) {
		this.i = i;
		this.j = j;
		
		sqrModule = i*i + j*j;
	}
	
	public BoardCoord plus(BoardCoord another) {
		return new BoardCoord(i + another.i, j + another.j);
	}
	
	public BoardCoord delta(BoardCoord another) {
		return new BoardCoord(i - another.i, j - another.j);
	}

	public boolean isDiagonal() {
		return i*i == j*j;
	}
	
	public boolean isOrthogonal() {
		return sqrModule != 0 && (i == 0 || j == 0);
	}

	public boolean unitary() {
		return sqrModule == 1 || sqrModule == 2;
	}

	public BoardCoord getDirection() {
		if (unitary()) {
			return this;
		}
		int di = i > 0? +1: i < 0? -1: 0;
		int dj = j > 0? +1: j < 0? -1: 0;
		return new BoardCoord(di, dj);
	}
	
}
