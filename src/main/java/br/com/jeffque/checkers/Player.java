package br.com.jeffque.checkers;

import br.com.jeffque.checkers.utils.BoardCoord;

public enum Player {
	WHITE,
	BLACK;
	
	public boolean isBackward(BoardCoord delta) {
		if (this == Player.WHITE) {
			return delta.i < 0;
		} else {
			return delta.i > 0;
		}
	}
}
