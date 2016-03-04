package br.com.jeffque.aspira.event;

import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.Perception;

public class SensorialEvent<P extends Perception, E extends Environment> extends EnvironmentEvent<E> {
	private P perception;
	public SensorialEvent(P perception) {
		this.perception = perception;
	}
	
	public P getPerception() {
		return perception;
	}

}
