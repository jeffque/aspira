package br.com.jeffque.aspira.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.obj.EnvironmentObject;

public abstract class AgentBody<E extends Environment> extends EnvironmentObject<E> {
	private Map<String, Sensor<?, E>> sensors = new HashMap<>();
	private Map<String, Actuator<?, E>> actuators = new HashMap<>();
	
	public AgentBody() {
	}
	
	public void addSensor(String id, Sensor<?,E> sensor) {
		sensors.put(id, sensor);
		addEmbeddedObject(sensor);
	}
	
	public void addActuator(String id, Actuator<?, E> actuator) {
		actuators.put(id, actuator);
		addEmbeddedObject(actuator);
	}

	public Actuator<?, E> getActuator(String id) {
		return actuators.get(id);
	}
}
