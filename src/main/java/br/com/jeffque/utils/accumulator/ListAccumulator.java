package br.com.jeffque.utils.accumulator;

import java.util.List;

import br.com.jeffque.utils.ListGenerator;

public class ListAccumulator<OP> implements Accumulator<List<OP>, OP> {
	private ListGenerator<OP> listGenerator;
	
	public ListAccumulator() {
		this(new ListGenerator<>());
	}
	
	public ListAccumulator(ListGenerator<OP> listGenerator) {
		this.listGenerator = listGenerator;
	}

	@Override
	public List<OP> firstElement() {
		return listGenerator.yield();
	}

	@Override
	public List<OP> accumulate(List<OP> accumulated, OP newOperator) {
		accumulated.add(newOperator);
		return accumulated;
	}

}
