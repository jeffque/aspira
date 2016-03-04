package br.com.jeffque.utils.accumulator;

import java.util.Map;

public interface AccumulationMap<K, R, OP> extends Map<K, R> {
	Accumulator<R, OP> getAccumulator();
	default R accumulate(K key, OP operator) {
		return put(key, getAccumulator().accumulateNS(get(key), operator));
	}
}
