package br.com.jeffque.checkers.actions;

import br.com.jeffque.aspira.env.Action;
import br.com.jeffque.checkers.utils.BoardCoord;

public class Move implements Action {
	private BoardCoord origin;
	private BoardCoord[] destinies;
	
	public Move(BoardCoord origin, BoardCoord... destinies) {
		this.origin = origin;
		this.destinies = destinies;
	}
	
	public BoardCoord getOrigin() {
		return origin;
	}
	
	public BoardCoord[] getDestinies() {
		return destinies;
	}

	public BoardCoord getFinalDestination() {
		return destinies[destinies.length - 1];
	}
}
