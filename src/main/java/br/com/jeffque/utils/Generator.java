package br.com.jeffque.utils;

public interface Generator<T> {
	T yield() throws Exception;
}
