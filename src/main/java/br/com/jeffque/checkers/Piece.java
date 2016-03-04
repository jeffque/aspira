package br.com.jeffque.checkers;

import br.com.jeffque.aspira.env.EnvironmentClass;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public class Piece extends EnvironmentObject<CheckerEnvironment> {
	private PieceType type;
	private final Player player;
	
	public Piece(Player player) {
		this.player = player;
		this.type = PieceType.MAN;
	}

	public Piece(Piece oriPiece) {
		this.player = oriPiece.player;
		this.type = oriPiece.type;
	}

	public PieceType getType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void promote() {
		type = PieceType.KING;
	}

	@Override
	public EnvironmentClass getEnvClass() {
		return EnvironmentClass.getRegisteredClass("checkers");
	}
	
}
