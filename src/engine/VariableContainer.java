package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.javafx.geom.Point2D;

/**
 * Holds variables of different types with String names. GameObject and GlobalVariables extend this.
 * 
 * @author Aaron Paskin
 *
 */
public abstract class VariableContainer {
	
	private static final boolean DEFAULT_BOOLEAN = false;
	private static final String DEFAULT_STRING = "";
	private static final int DEFAULT_DOUBLE = 0;
	
	protected Map<String, Double> doubleVars;
	protected Map<String, List<Double>> doubleListVars;
	
	protected Map<String, Point2D> vectorVars;
	
	protected Map<String, String> stringVars;
	protected Map<String, List<String>> stringListVars;
	
	protected Map<String, Boolean> booleanVars;
	
	protected Map<String, GameObject> gameObjectVars;

	public VariableContainer() {
		doubleVars = new HashMap<String, Double>();
		doubleListVars = new HashMap<String, List<Double>>();
		
		vectorVars = new HashMap<String, Point2D>();
		
		stringVars = new HashMap<String, String>();
		stringListVars = new HashMap<String, List<String>>();
		
		booleanVars = new HashMap<String, Boolean>();
		
		gameObjectVars = new HashMap<String, GameObject>();
	}
	
	public double getDouble(String key) {
		if(doubleVars.containsKey(key))
			return doubleVars.get(key);
		return DEFAULT_DOUBLE;
	}
	
	public List<Double> getDoubleList(String key) {
		return doubleListVars.get(key);
	}
	
	public Point2D getVector(String key) {
		return vectorVars.get(key);
	}
	
	public String getString(String key) {
		if(stringVars.containsKey(key))
			return stringVars.get(key);
		return DEFAULT_STRING;
	}
	
	public List<String> getStringList(String key) {
		return stringListVars.get(key);
	}
	
	public boolean getBoolean(String key) {
		if(booleanVars.containsKey(key))
			return booleanVars.get(key);
		return DEFAULT_BOOLEAN;
	}

	public void setDoubleVariable(String name, double val) {
		doubleVars.put(name, val);
	}
	
	public void setDoubleListVariable(String name, List<Double> val) {
		doubleListVars.put(name, val);
	}
	
	public void setVectorVariable(String name, Point2D val) {
		vectorVars.put(name, val);
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
