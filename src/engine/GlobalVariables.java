package engine;

import java.util.ArrayList;

public class GlobalVariables extends VariableContainer {
	
	public GlobalVariables() {
		super();
	}
	
	public void putString(String key, String newString) {
		stringVars.put(key, newString);
	}
	
	public void putBoolean(String key, boolean value) {
		booleanVars.put(key, value);
	}
	
}
