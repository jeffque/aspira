package br.com.jeffque.aspira.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.jeffque.aspira.event.ActionEvent;
import br.com.jeffque.aspira.event.ActionListener;
import br.com.jeffque.aspira.event.SensorialEvent;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public abstract class Environment {
	private final EnvironmentClass envClass;
	private List<ActionListener<?, ?>> actionListeners = new ArrayList<>();
	
	public void addActionListener(ActionListener<?, ?> listener) {
		actionListeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	public <A extends Action, E extends Environment> void sendAction(ActionEvent<A, E> event) {
		for (@SuppressWarnings("rawtypes") ActionListener listener: actionListeners) {
			if (listener.relevantAction(event)) {
				Map<EnvironmentObject<E>, List<SensorialEvent<?, E>>> mapSensorialEvents = listener.interceptAction(event);
				
				for (Entry<EnvironmentObject<E>, List<SensorialEvent<?, E>>> pairSense: mapSensorialEvents.entrySet()) {
					EnvironmentObject<E> envObj = pairSense.getKey();
					List<SensorialEvent<?, E>> events = pairSense.getValue();
					
					for (SensorialEvent<?, E> sensorialEvent: events) {
						envObj.receiveSensorialEvent(sensorialEvent);
					}
				}
			}
		}
	}

	public Environment(EnvironmentClass envClass) {
		this.envClass = envClass;
	}

	public EnvironmentClass getEnvClass() {
		return envClass;
	}
}
