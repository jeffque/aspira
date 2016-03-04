package br.com.jeffque.utils;

import java.util.ArrayList;
import java.util.List;

public class ListGenerator<T> implements Generator<List<T>> {

	@Override
	public List<T> yield() {
		return new ArrayList<>();
	}

}
