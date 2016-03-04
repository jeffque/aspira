package br.com.jeffque.utils.accumulator;

import java.util.List;

public class AccumulationListHashMap<K, OP> extends AccumulationHashMap<K, List<OP>, OP> {
	private static final long serialVersionUID = 659443130966906486L;
	private ListAccumulator<OP> accumulator = new ListAccumulator<OP>();

	@Override
	public ListAccumulator<OP> getAccumulator() {
		return accumulator;
	}

}
