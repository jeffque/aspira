package br.com.jeffque.checkers;

import java.util.List;
import java.util.Map;

import br.com.jeffque.aspira.env.EnvironmentClass;
import br.com.jeffque.aspira.event.ActionEvent;
import br.com.jeffque.aspira.event.ActionListener;
import br.com.jeffque.aspira.event.SensorialEvent;
import br.com.jeffque.aspira.obj.EnvironmentObject;
import br.com.jeffque.checkers.actions.Capture;
import br.com.jeffque.checkers.actions.Move;
import br.com.jeffque.checkers.utils.BoardCoord;
import br.com.jeffque.utils.accumulator.AccumulationListHashMap;

public class CheckerBoard extends EnvironmentObject<CheckerEnvironment> {
	{
		addActionListener(new ActionListener<CheckerEnvironment, Move>() {

			@Override
			public Map<EnvironmentObject<CheckerEnvironment>, List<SensorialEvent<?, CheckerEnvironment>>> interceptAction(ActionEvent<Move, CheckerEnvironment> actionEvent) {
				Move act = actionEvent.getAction();
				Piece movedPiece = getPiece(act.getOrigin());
				setPiece(act.getOrigin(), null);
				setPiece(act.getFinalDestination(), movedPiece);
				return new AccumulationListHashMap<>();
			}

			@Override
			public boolean relevantAction(ActionEvent<?, ?> ae) {
				return ae.getAction() instanceof Move;
			}
		});
		
		addActionListener(new ActionListener<CheckerEnvironment, Capture>() {

			@Override
			public Map<EnvironmentObject<CheckerEnvironment>, List<SensorialEvent<?, CheckerEnvironment>>> interceptAction(ActionEvent<Capture, CheckerEnvironment> actionEvent) {
				Capture act = actionEvent.getAction();
				setPiece(act.getCapturedCoord(), null);
				return new AccumulationListHashMap<>();
			}

			@Override
			public boolean relevantAction(ActionEvent<?, ?> ae) {
				return ae.getAction() instanceof Capture;
			}
		});
	}
	private Piece[][] board;
	
	public Piece[][] getBoard() {
		return createCopy().board;
	}
	
	protected void setPiece(BoardCoord position, Piece piece) {
		board[position.i][position.j] = piece;
	}

	public CheckerBoard createCopy() {
		return new CheckerBoard(board);
	}

	private int initialDeltaJ(int i) {
		if (i % 2 == 0) {
			return 1;
		} else {
			return 2;
		}
	}
	
	private void populateDiagonal(int sizeX, int sizeY, int initialRowsPopulation) {
		for (int i = initialRowsPopulation - 1; i >= 0; i--) {
			for (int j = sizeY - initialDeltaJ(i); j >= 0; j-= 2) {
				board[i][j] = new Piece(Player.WHITE);
			}
		}
		
		for (int i = sizeX - 1; i >= sizeX - initialRowsPopulation; i--) {
			for (int j = sizeY - initialDeltaJ(i); j >= 0; j-= 2) {
				board[i][j] = new Piece(Player.BLACK);
			}
		}
	}
	
	private void populateOrtogonal(int sizeX, int sizeY, int initialRowsPopulation) {
		for (int i = initialRowsPopulation - 1; i >= 0; i--) {
			for (int j = sizeY - 1; j >= 0; j--) {
				board[i][j] = new Piece(Player.WHITE);
			}
		}
		
		for (int i = sizeX - 1; i >= sizeX - initialRowsPopulation; i--) {
			for (int j = sizeY - 1; j >= 0; j--) {
				board[i][j] = new Piece(Player.BLACK);
			}
		}
	}
	
	public CheckerBoard(int sizeX, int sizeY, int initialRowsPopulation, MovementType moveType) {
		board = new Piece[sizeX][];
		
		for (int i = sizeX - 1; i >= 0; i--) {
			board[i] = new Piece[sizeY];
		}
		
		switch (moveType) {
		case DIAGONAL:
			populateDiagonal(sizeX, sizeY, initialRowsPopulation);
			break;
		default:
			populateOrtogonal(sizeX, sizeY, initialRowsPopulation);
			break;
		}
	}

	public CheckerBoard(Piece[][] boardOriginal) {
		int sizeX = boardOriginal.length;
		int sizeY = boardOriginal[0].length;
		
		board = new Piece[sizeX][];

		for (int i = sizeX - 1; i >= 0; i--) {
			board[i] = new Piece[sizeY];
		}
		
		for (int i = sizeX - 1; i >= 0; i--) {
			for (int j = sizeY - 1; j >= 0; j--) {
				Piece oriPiece = boardOriginal[i][j];
				if (oriPiece != null) {
					board[i][j] = new Piece(oriPiece);
				}
			}
		}
	}
	
	public Piece getPiece(BoardCoord coord) {
		return getPiece(coord.i, coord.j);
	}

	public Piece getPiece(int i, int j) {
		return board[i][j];
	}

	@Override
	public EnvironmentClass getEnvClass() {
		return EnvironmentClass.getRegisteredClass("checkers");
	}
	
}
