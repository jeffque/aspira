package br.com.jeffque.checkers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.jeffque.aspira.agent.AgentBody;
import br.com.jeffque.aspira.agent.AgentMetaBody;
import br.com.jeffque.aspira.agent.AgentSoul;
import br.com.jeffque.aspira.agent.Sensor;
import br.com.jeffque.aspira.checkers.Mover;
import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.EnvironmentClass;
import br.com.jeffque.aspira.env.Perception;
import br.com.jeffque.aspira.event.ActionEvent;
import br.com.jeffque.aspira.event.ActionListener;
import br.com.jeffque.aspira.event.SensorialEvent;
import br.com.jeffque.aspira.obj.EnvironmentObject;
import br.com.jeffque.checkers.actions.Capture;
import br.com.jeffque.checkers.actions.Move;
import br.com.jeffque.checkers.perceptions.InvalidMove;
import br.com.jeffque.checkers.utils.BoardCoord;
import br.com.jeffque.utils.accumulator.AccumulationListHashMap;

public class CheckerEnvironment extends Environment {
	private final boolean flyingKing;
	private final boolean captureBackward;
	private final int sizeX;
	private final int sizeY;

	private final MovementType moveType;
	private final CheckerBoard board;
	
	public class PlayerAgentBody extends AgentBody<CheckerEnvironment> {
		private final Player player;
		
		public PlayerAgentBody(Player player) {
			this.player = player;
			
			addActuator("MOVER", new Mover());
		}
		
		public Player getPlayer() {
			return player;
		}

		@Override
		public EnvironmentClass getEnvClass() {
			return CheckerEnvironment.this.getEnvClass();
		}
	}
	
	public PlayerAgentBody whitePlayerBody;
	public PlayerAgentBody blackPlayerBody;
	
	{
		whitePlayerBody = new PlayerAgentBody(Player.WHITE);
		blackPlayerBody = new PlayerAgentBody(Player.BLACK);
		
		whitePlayerBody.addOrReplaceEnvironment(this);
		blackPlayerBody.addOrReplaceEnvironment(this);
	}
	
	static {
		CheckerClass.registerSelf();
	}
	
	public class MoveTypeSense implements Perception {
		public MovementType moveType() {
			return CheckerEnvironment.this.moveType;
		}
	}
	
	public class MoveTypeSensor extends Sensor<MoveTypeSense, CheckerEnvironment> {
		private MoveTypeSense sense = new MoveTypeSense();
		private List<MoveTypeSense> senses;
		{
			senses = new ArrayList<>();
			senses.add(sense);
		}
		
		@Override
		public List<MoveTypeSense> sense() {
			return senses;
		}

		@Override
		public EnvironmentClass getEnvClass() {
			return CheckerEnvironment.this.getEnvClass();
		}
		
	}

	
	public class FlyingKingSense implements Perception {
		public boolean hasFlyingKing() {
			return CheckerEnvironment.this.flyingKing;
		}
	}
	
	public class HasFlyingKing extends Sensor<FlyingKingSense, CheckerEnvironment> {
		private FlyingKingSense sense = new FlyingKingSense();
		private List<FlyingKingSense> senses;
		{
			senses = new ArrayList<>();
			senses.add(sense);
		}
		
		@Override
		public List<FlyingKingSense> sense() {
			return senses;
		}

		@Override
		public EnvironmentClass getEnvClass() {
			return CheckerEnvironment.this.getEnvClass();
		}
		
	}
	
	public class CaptureBackwardSense implements Perception {
		public boolean hasCaptureBackward() {
			return CheckerEnvironment.this.captureBackward;
		}
	}
	
	public class HasCaptureBackward extends Sensor<CaptureBackwardSense, CheckerEnvironment> {
		private CaptureBackwardSense sense = new CaptureBackwardSense();
		private List<CaptureBackwardSense> senses;
		{
			senses = new ArrayList<>();
			senses.add(sense);
		}
		
		@Override
		public List<CaptureBackwardSense> sense() {
			return senses;
		}

		@Override
		public EnvironmentClass getEnvClass() {
			return CheckerEnvironment.this.getEnvClass();
		}
		
	}
	
	public class SizeSense implements Perception {
		public int getSizeX() {
			return CheckerEnvironment.this.sizeX;
		}
		
		public int getSizeY() {
			return CheckerEnvironment.this.sizeY;
		}
	}
	
	public class SizeSensor extends Sensor<SizeSense, CheckerEnvironment> {
		private SizeSense sense = new SizeSense();
		private List<SizeSense> senses;
		{
			senses = new ArrayList<>();
			senses.add(sense);
		}
		
		@Override
		public List<SizeSense> sense() {
			return senses;
		}

		@Override
		public EnvironmentClass getEnvClass() {
			return CheckerEnvironment.this.getEnvClass();
		}
		
	}
	
	public CheckerEnvironment(int nRowsPopulated, int sizeX, int sizeY, boolean flyingKing, boolean captureBackward, MovementType moveType) {
		super(EnvironmentClass.getRegisteredClass("checkers"));
		
		
		this.moveType = moveType;
		this.flyingKing = flyingKing;
		this.captureBackward = captureBackward;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.board = new CheckerBoard(sizeX, sizeY, nRowsPopulated, moveType);
		
		addActionListener(new ActionListener<CheckerEnvironment, Move>() {
			class InvalidMoveException extends Exception {
				final Perception p;
				InvalidMoveException(Perception p) {
					this.p = p;
				}
			}
			
			InvalidMoveException getInvalidMoveException() {
				return new InvalidMoveException(new InvalidMove());
			}
			
			@Override
			public Map<EnvironmentObject<CheckerEnvironment>, List<SensorialEvent<?, CheckerEnvironment>>> interceptAction(ActionEvent<Move, CheckerEnvironment> actionEvent) {
				AccumulationListHashMap<EnvironmentObject<CheckerEnvironment>, SensorialEvent<?, CheckerEnvironment>> eventsMap = new AccumulationListHashMap<>();
				Move act = actionEvent.getAction();
				Player senderPlayer = ((PlayerAgentBody) actionEvent.getSender()).getPlayer();
				
				Piece movedPiece = board.getPiece(act.getOrigin());
				
				try {
					// The player must move his piece
					if (movedPiece == null) {
						throw getInvalidMoveException();
					} else if (movedPiece.getPlayer() != senderPlayer) {
						throw getInvalidMoveException();
					}
					
					List<BoardCoord> capturedPieces = new ArrayList<>();
					boolean canMoveBackward = movedPiece.getType() == PieceType.KING;
					boolean canCaptureBackward = movedPiece.getType() == PieceType.KING || CheckerEnvironment.this.captureBackward;
					boolean flyingKing = movedPiece.getType() == PieceType.KING && CheckerEnvironment.this.flyingKing;
					BoardCoord lastCoord = act.getOrigin();
					for (BoardCoord nextCoord: act.getDestinies()) {
						// The space must be empty
						if (board.getPiece(nextCoord) != null) {
							throw getInvalidMoveException();
						}
						BoardCoord delta = nextCoord.delta(lastCoord);
						
						boolean diagonalValid = false;
						boolean orthogonalValid = false;
						
						diagonalValid = delta.isDiagonal();
						orthogonalValid = delta.isOrthogonal();
						
						boolean validFormat;
						
						switch (CheckerEnvironment.this.moveType) {
						case DIAGONAL:
							validFormat = diagonalValid;
							break;
						case ORTHOGONAL:
							validFormat = orthogonalValid;
							break;
						case MIXED:
						default:
							validFormat = diagonalValid || orthogonalValid;
							break;
						}
						
						// Must have a valid format
						if (!validFormat) {
							throw getInvalidMoveException();
						}
						
						BoardCoord direction = delta.getDirection();
						
						boolean capture = false;
						for (BoardCoord inter = lastCoord.plus(direction), until = nextCoord.plus(direction); !inter.equiv(until); inter = inter.plus(direction)) {
							Piece pieceInTheMiddle = board.getPiece(inter);
							
							if (pieceInTheMiddle == null) {
								// Ignore blank space
								continue;
							} else if (pieceInTheMiddle.getPlayer() == senderPlayer) {
								// Can't capture own piece
								throw getInvalidMoveException();
							} else if (!capture) {
								// First and wanna be only capture detected
								capture = true;
								capturedPieces.add(inter);
							} else {
								// At most one capture per destiny
								throw getInvalidMoveException();
							}
						}
						
						// Either this was a non-backward move, or it can move backward, or it can capture backward and captured stuff
						if (!senderPlayer.isBackward(direction) || canMoveBackward || (canCaptureBackward && capture)) {
							// Nice!!
						} else {
							// Oops?
							throw getInvalidMoveException();
						}
						
						// Either flying king, or unitary, or capture with module 4 or 8 to be valid
						if (flyingKing || delta.unitary() || (capture && (delta.sqrModule == 4 || delta.sqrModule == 8))) {
							// Nice!!
						} else {
							// Oops?
							throw getInvalidMoveException();
						}
						lastCoord = nextCoord;
					}
					
					// If I am doing more than one move, then all moves must capture stuff
					if (act.getDestinies().length != 1 && act.getDestinies().length != capturedPieces.size()) {
						throw getInvalidMoveException();
					}
					
					// Ok, so this was indeed a valid move...
					board.sendAction(actionEvent);
					
					for (BoardCoord captured: capturedPieces) {
						board.sendAction(new ActionEvent<>(new Capture(captured), CheckerEnvironment.this, board));
					}
				} catch (InvalidMoveException e) {
					eventsMap.accumulate(actionEvent.getSender(), new SensorialEvent<>(new InvalidMove()));
				}
				
				return eventsMap;
			}

			@Override
			public boolean relevantAction(ActionEvent<?, ?> ae) {
				return ae.getAction() instanceof Move;
			}
		});
	}

	public CheckerEnvironment(int nRowsPopulated, boolean flyingKing, boolean captureBackward) {
		this(nRowsPopulated, 8, 8, flyingKing, captureBackward, MovementType.DIAGONAL);
	}

	public CheckerEnvironment() {
		this(3, true, true);
	}

	public CheckerBoard getBoardCopy() {
		return board.createCopy();
	}

	public void setPlayerSoul(AgentSoul agentSoul, Player player) {
		PlayerAgentBody playerBody;
		
		switch (player) {
		case BLACK:
			playerBody = blackPlayerBody;
			break;
		case WHITE:
			playerBody = whitePlayerBody;
			break;
		default:
			return;
		}
		AgentMetaBody metaBody = new AgentMetaBody();
		metaBody.addBody("PLAYER", playerBody);
		agentSoul.linkMetaBody(metaBody);
	}
}
