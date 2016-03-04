package br.com.jeffque.aspira.event;

import java.util.List;
import java.util.Map;

import br.com.jeffque.aspira.env.Action;
import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public interface ActionListener<E extends Environment, A extends Action> {
	Map<EnvironmentObject<E>, List<SensorialEvent<?, E>>> interceptAction(ActionEvent<A, E> actionEvent);
	
	boolean relevantAction(ActionEvent<?, ?> ae);
}
