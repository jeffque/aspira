package br.com.jeffque.aspira.agent;

import java.util.List;

import br.com.jeffque.aspira.env.Environment;
import br.com.jeffque.aspira.env.EnvironmentClass;
import br.com.jeffque.aspira.obj.MetaBody;

public class AgentMetaBody extends MetaBody {
	
	@Override
	public AgentBody<?> getBody(String id) {
		return (AgentBody<?>) super.getBody(id);
	}
	
	public <E extends Environment, R extends AgentBody<E>> List<R> getAgentBodiesFromEnvClass(EnvironmentClass envClass) {
		return super.getBodiesFromEnvClass(envClass);
	}
	
}
