package engine;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;

/**
 * Holds variables of different types with String names. GameObject and
 * GlobalVariables extend this.
 * 
 * @author Aaron Paskin
 *
 */
public abstract class VariableContainer {

	private static final boolean DEFAULT_BOOLEAN = false;
	private static final String DEFAULT_STRING = "";
	private static final int DEFAULT_DOUBLE = 0;

	protected Map<String, Double> doubleVars;
	protected Map<String, String> stringVars;
	protected Map<String, Boolean> booleanVars;

	public VariableContainer() {
		doubleVars = new HashMap<String, Double>();
		stringVars = new HashMap<String, String>();
		booleanVars = new HashMap<String, Boolean>();
	}
	
	public Map<String, Double> getAllDoubleVars() {
		return doubleVars;
	}
	
	public Map<String, String> getAllStringVars() {
		return stringVars;
	}
	
	public Map<String, Boolean> getAllBooleanVars() {
		return booleanVars;
	}
	
	public double getDouble(String key) {
		if (doubleVars.containsKey(key))
			return doubleVars.get(key);
		return DEFAULT_DOUBLE;
	}

	public String getString(String key) {
		if (stringVars.containsKey(key))
			return stringVars.get(key);
		return DEFAULT_STRING;
	}

	public boolean getBoolean(String key) {
		if (booleanVars.containsKey(key))
			return booleanVars.get(key);
		return DEFAULT_BOOLEAN;
	}

	public void setDoubleVariable(String name, double val) {
		doubleVars.put(name, val);
	}

	public void setStringVariable(String name, String val) {
		stringVars.put(name, val);
	}

	public void setBooleanVariable(String name, boolean val) {
		booleanVars.put(name, val);
	}
}
