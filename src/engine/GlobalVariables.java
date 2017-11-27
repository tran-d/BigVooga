package engine;

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
	
	public void putDouble(String key, double value) {
		doubleVars.put(key, value);
	}
	
}
