package br.com.jeffque.aspira.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.jeffque.aspira.env.Action;
import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.EnvironmentClass;
import br.com.jeffque.aspira.env.Perception;
import br.com.jeffque.aspira.event.ActionEvent;
import br.com.jeffque.aspira.event.ActionListener;
import br.com.jeffque.aspira.event.SensorialEvent;
import br.com.jeffque.aspira.event.SensorialListener;

public abstract class EnvironmentObject<E extends Environment> {
	public abstract EnvironmentClass getEnvClass();
	private E currentEnvironment;
	
	private List<ActionListener<?, ?>> actionListeners = new ArrayList<>();
	
	public void addActionListener(ActionListener<?, ?> listener) {
		actionListeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	public <A extends Action> void sendAction(ActionEvent<A, E> event) {
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
	
	private List<SensorialListener<E, ?, ?>> sensorialListeners = new ArrayList<>();
	
	public void addSensorialListener(SensorialListener<E, ?, ?> listener) {
		sensorialListeners.add(listener);
	}
	
	@SuppressWarnings("unchecked")
	public <P extends Perception> void receiveSensorialEvent(SensorialEvent<P, E> event) {
		for (@SuppressWarnings("rawtypes") SensorialListener listener: sensorialListeners) {
			if (listener.relevantPerception(event)) {
				Map<EnvironmentObject<E>, List<SensorialEvent<?, E>>> mapSensorialEvents = listener.interceptPerception(event);
				
				for (Entry<EnvironmentObject<E>, List<SensorialEvent<?, E>>> pairSense: mapSensorialEvents.entrySet()) {
					EnvironmentObject<E> envObj = pairSense.getKey();
					List<SensorialEvent<?, E>> events = pairSense.getValue();
					
					for (SensorialEvent<?, E> sensorialEvent: events) {
						envObj.receiveSensorialEvent(sensorialEvent);
					}
				}
			}
		}
		parentObject.receiveSensorialEvent(event);
	}
	
	private EnvironmentObject<E> parentObject;
	private List<EnvironmentObject<E>> embeddedObjects = new ArrayList<>();
	public void addEmbeddedObject(EnvironmentObject<E> embedded) {
		embeddedObjects.add(embedded);
		embedded.parentObject = this;
	}
	
	public E getCurrentEnvironment() {
		return currentEnvironment;
	}
	
	public void addOrReplaceEnvironment(E newEnv) {
		this.currentEnvironment = newEnv;
		for (EnvironmentObject<E> embedded: embeddedObjects) {
			embedded.addOrReplaceEnvironment(newEnv);
		}
	}

}
