package br.com.jeffque.checkers;

import br.com.jeffque.aspira.env.EnvironmentClass;

public class CheckerClass extends EnvironmentClass {
	private static boolean registered = false;

	protected CheckerClass() {
		super();
	}

	public static void registerSelf() {
		if (!registered) {
			new CheckerClass();
		}
	}

	@Override
	public String getClassId() {
		return "checkers";
	}

}
