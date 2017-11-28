package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds variables of different types with String names. Create an instance of this for GlobalVariables. GameObject extends this.
 * 
 * @author aaronpaskin
 *
 */
public abstract class VariableContainer {
	
	protected Map<String, Double> doubleVars;
	protected Map<String, List<Double>> doubleListVars;
	
	protected Map<String, String> stringVars;
	protected Map<String, List<String>> stringListVars;
	
	protected Map<String, Boolean> booleanVars;

	public VariableContainer() {
		// TODO Auto-generated constructor stub
		doubleVars = new HashMap<String, Double>();
		stringVars = new HashMap<String, String>();
	}
	
	public double getDouble(String key) {
		return doubleVars.get(key);
	}
	
	public List<Double> getDoubleList(String key) {
		return doubleListVars.get(key);
	}
	
	public String getString(String key) {
		return stringVars.get(key);
	}
	
	public List<String> getStringList(String key) {
		return stringListVars.get(key);
	}
	
	public boolean getBoolean(String key) {
		return booleanVars.get(key);
	}

	public void setDoubleVariable(String name, double val) {
		doubleVars.put(name, val);
	}
	
	public void setDoubleListVariable(String name, List<Double> val) {
		doubleListVars.put(name, val);
	}
	
	public void setStringVariable(String name, String val) {
		stringVars.put(name, val);
	}
	
	public void setStringListVariable(String varName, List<String> val) {
		stringListVars.put(varName, val);
	}
	
	public void setBooleanVariable(String name, boolean val) {
		booleanVars.put(name, val);
	}
}
