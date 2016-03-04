package br.com.jeffque.checkers.actions;

import br.com.jeffque.aspira.env.Action;
import br.com.jeffque.checkers.utils.BoardCoord;

public class Capture implements Action {
	private BoardCoord coord;
	
	public Capture(BoardCoord coord) {
		this.coord = coord;
	}
	
	public BoardCoord getCapturedCoord() {
		return coord;
	}
}
