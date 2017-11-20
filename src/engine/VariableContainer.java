package engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds variables of different types with String names. Create an instance of this for GlobalVariables. GameObject extends this.
 * 
 * @author aaronpaskin
 *
 */
public class VariableContainer {
	
	private Map<String, Double> doubleVars;
	private Map<String, String> stringVars;
	private Map<String, Boolean> booleanVars;

	public VariableContainer() {
		// TODO Auto-generated constructor stub
		doubleVars = new HashMap<String, Double>();
		stringVars = new HashMap<String, String>();
	}
	
	public String getString(String key) {
		return stringVars.get(key);
	}
	
	public double getDouble(String key) {
		return doubleVars.get(key);
	}
	
	public boolean getBoolean(String key) {
		return booleanVars.get(key);
	}

}
