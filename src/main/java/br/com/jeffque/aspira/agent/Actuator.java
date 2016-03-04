package br.com.jeffque.aspira.agent;

import br.com.jeffque.aspira.env.Action;
import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.event.ActionEvent;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public abstract class Actuator<A extends Action, E extends Environment> extends EnvironmentObject<E> {

	public Actuator() {
	}
	
	public void act(EnvironmentObject<E> sender) {
		getCurrentEnvironment().sendAction(new ActionEvent<>(getAction(), getCurrentEnvironment(), sender));
	}

	public abstract A getAction();
}
