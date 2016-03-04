package br.com.jeffque.aspira.checkers;

import java.util.ArrayList;
import java.util.List;

import br.com.jeffque.aspira.agent.Actuator;
import br.com.jeffque.aspira.env.EnvironmentClass;
import br.com.jeffque.checkers.actions.Move;
import br.com.jeffque.checkers.utils.BoardCoord;
import br.com.jeffque.checkers.CheckerEnvironment;

public class Mover extends Actuator<Move, CheckerEnvironment> {
	private BoardCoord origin;
	private List<BoardCoord> destinies = new ArrayList<>();
	
	public void setOrigin(BoardCoord origin) {
		this.origin = origin;
	}
	
	public void clearDestinies() {
		destinies.clear();
	}
	
	public void addDestiny(BoardCoord newDestiny) {
		destinies.add(newDestiny);
	}

	@Override
	public Move getAction() {
		return new Move(origin, destinies.toArray(new BoardCoord[0]));
	}

	@Override
	public EnvironmentClass getEnvClass() {
		return EnvironmentClass.getRegisteredClass("checkers");
	}

}
