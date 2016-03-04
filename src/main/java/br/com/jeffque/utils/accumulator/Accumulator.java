package br.com.jeffque.utils.accumulator;

public interface Accumulator<R, OP> {
	R firstElement();
	R accumulate(R accumulated, OP newOperator);
	
	default R accumulateNS(R accumulated, OP newOperator) {
		return accumulate(accumulated != null? accumulated: firstElement(), newOperator);
	}
}
