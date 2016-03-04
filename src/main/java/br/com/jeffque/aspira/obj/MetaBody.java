package br.com.jeffque.aspira.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.EnvironmentClass;

public class MetaBody {
	private Map<String, EnvironmentObject<?>> bodies = new HashMap<>();
	
	public void addBody(String id, EnvironmentObject<?> body) {
		bodies.put(id, body);
	}
	
	public EnvironmentObject<?> getBody(String id) {
		return bodies.get(id);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Environment, R extends EnvironmentObject<E>> List<R> getBodiesFromEnvClass(EnvironmentClass envClass) {
		List<R> foundBodies = new ArrayList<>();
		
		for (EnvironmentObject<?> body: bodies.values()) {
			if (body.getEnvClass().equals(envClass)) {
				foundBodies.add((R) body);
			}
		}
		
		return foundBodies;
	}
}
