package br.com.jeffque.aspira.env;

import java.util.HashMap;
import java.util.Map;

public abstract class EnvironmentClass {
	private static Map<String, EnvironmentClass> registeredClasses = new HashMap<>();
	
	public static void registerClass(String classId, EnvironmentClass envClass) {
		registeredClasses.put(classId, envClass);
	}
	
	public static EnvironmentClass getRegisteredClass(String classId) {
		return registeredClasses.get(classId);
	}
	
	public abstract String getClassId();
	
	protected EnvironmentClass() {
		registerClass(getClassId(), this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else if (this == obj) {
			return true;
		} else if (!(obj instanceof EnvironmentClass)) {
			return false;
		}
		EnvironmentClass objMyClass = (EnvironmentClass) obj;
		return getClassId().equals(objMyClass.getClassId());
	}

	@Override
	public int hashCode() {
		return getClassId().hashCode();
	}

	@Override
	public String toString() {
		return getClassId();
	}
	
	
}
