package br.com.jeffque.aspira.agent;

import java.util.List;

import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.Perception;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public abstract class Sensor<I extends Perception, E extends Environment> extends EnvironmentObject<E> {

	public Sensor() {
	}
	
	public abstract List<I> sense();

}
