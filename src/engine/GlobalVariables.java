package engine;

import java.util.ArrayList;

public class GlobalVariables extends VariableContainer {
	
	public static final String LAST_KEYS = "LastKeysHeld";
	public static final String LAST_CLICK = "LastClicked";
	
	public GlobalVariables() {
		super();
		stringListVars.put(LAST_KEYS, new ArrayList<String>());
		booleanVars.put(LAST_CLICK, false);
	}
	
	public void putString(String key, String newString) {
		stringVars.put(key, newString);
	}
	
	public void putBoolean(String key, boolean value) {
		booleanVars.put(key, value);
	}
	
}
