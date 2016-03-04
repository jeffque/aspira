package br.com.jeffque.aspira.event;

import java.util.List;
import java.util.Map;

import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.Perception;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public interface SensorialListener<E extends Environment, P extends Perception, SV extends SensorialEvent<P, E>> {
	Map<EnvironmentObject<E>, List<SensorialEvent<?, E>>> interceptPerception(SV sensorialEvent);
	
	boolean relevantPerception(SensorialEvent<?, ?> ae);
}
