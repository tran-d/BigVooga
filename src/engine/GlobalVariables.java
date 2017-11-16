package engine;

import java.util.HashMap;
import java.util.Map;

public class GlobalVariables {
	
	private Map<String, Double> doubleVars;
	private Map<String, String> stringVars;

	public GlobalVariables() {
		// TODO Auto-generated constructor stub
		doubleVars = new HashMap<String, Double>();
		stringVars = new HashMap<String, String>();
	}
	
	public String getString(String key)
	{
		return stringVars.get(key);
	}
	
	public double getDouble(String key)
	{
		return doubleVars.get(key);
	}

}
