package br.com.jeffque.aspira.checkers;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.jeffque.aspira.agent.AgentBody;
import br.com.jeffque.aspira.agent.AgentSoul;
import br.com.jeffque.checkers.CheckerBoard;
import br.com.jeffque.checkers.CheckerEnvironment;
import br.com.jeffque.checkers.Piece;
import br.com.jeffque.checkers.PieceType;
import br.com.jeffque.checkers.Player;
import br.com.jeffque.checkers.utils.BoardCoord;

public class TestCheckerBoard {
	
	private void printBoard(CheckerBoard board) {
		printBoard(board.getBoard());
	}
		
	private void printBoard(Piece[][] pieces) {
		StringBuilder stringRepresentation = new StringBuilder();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = pieces[i][j];
				if (piece == null) {
					stringRepresentation.append("_");
				} else {
					String letter;
					
					switch (piece.getPlayer()) {
					case BLACK:
						letter = "b";
						break;
					case WHITE:
						letter = "w";
						break;
					default:
						letter = "u";
						break;
					}
					
					if (piece.getType() == PieceType.KING) {
						letter = letter.toUpperCase();
					}
					
					stringRepresentation.append(letter);
				}
			}
			
			stringRepresentation.append("\n");
		}
		
		System.out.println(stringRepresentation);
	}

	@Test
	public void test() {
		CheckerEnvironment env = new CheckerEnvironment();
		AgentSoul as;
		env.setPlayerSoul(as = new AgentSoul() {

			@Override
			public void initRun() {
			}

			@SuppressWarnings("unchecked")
			@Override
			public void act() {
				AgentBody<CheckerEnvironment> body = (AgentBody<CheckerEnvironment>) getMetaBody().getBody("PLAYER");
				Mover m = (Mover) body.getActuator("MOVER");
				m.setOrigin(new BoardCoord(2, 1));
				m.addDestiny(new BoardCoord(3, 0));
				
				m.act(body);
			}

			@Override
			public void goalOptimizer() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void perceive() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean mustGoOn() {
				// TODO Auto-generated method stub
				return false;
			}
		}, Player.WHITE);

		try {
			printBoard(env.getBoardCopy());
			as.act();
			printBoard(env.getBoardCopy());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
