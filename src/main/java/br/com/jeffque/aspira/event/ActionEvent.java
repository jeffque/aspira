package br.com.jeffque.aspira.event;

import br.com.jeffque.aspira.env.Action;
import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public class ActionEvent<A extends Action, E extends Environment> extends EnvironmentEvent<E> {
	private A action;
	private E env;
	private EnvironmentObject<E> sender;
	
	public ActionEvent(A action, E env, EnvironmentObject<E> sender) {
		this.action = action;
		this.env = env;
		this.sender = sender;
	}

	public A getAction() {
		return action;
	}

	public E getEnv() {
		return env;
	}

	public EnvironmentObject<E> getSender() {
		return sender;
	}
}
