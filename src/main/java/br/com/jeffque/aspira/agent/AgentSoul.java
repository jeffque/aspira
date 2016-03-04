package br.com.jeffque.aspira.agent;

public abstract class AgentSoul {
	private AgentMetaBody metaBody;

	public AgentSoul() {
	}
	
	public void linkMetaBody(AgentMetaBody metaBody) {
		this.metaBody = metaBody;
	}
	
	public abstract void initRun();

	public AgentMetaBody getMetaBody() {
		return metaBody;
	}
	
	public void agentLoop() {
		while (mustGoOn()) {
			perceive();
			goalOptimizer();
			act();
		}
	}

	public abstract void act();
	public abstract void goalOptimizer();
	public abstract void perceive();
	public abstract boolean mustGoOn();

}
