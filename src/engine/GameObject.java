package engine;

import java.util.List;
import java.util.Set;

public interface GameObject {
	
//	private List<String> tags;
//	private Map<Condition, List<Action>> events;
//	private Map<String, Integer> intVars;
//	private Map<String, Double> doubleVars;
//	private Map<String, String> StringVars;
	
	public void addTag(String tag);

	public boolean is(String tag);
	
	public String getName();

	public List<String> getTags();
	
	public void setCoords(double x, double y);
	
	public double getX();
	
	public double getY();
	
	public void setHeading(double newHeading);
	
	public double getHeading();

	public void setGlobal(String variableName, boolean global);

	public void makeAllGlobal();

	public void setIntegerVariable(String name, int val);

	public void setDoubleVariable(String name, double val);

	public void setStringVariable(String name, String val);

	public void addToObjectList(GameObject o);

	public void addConditionAction(Condition c, List<Action> a);
	
	// TODO Add to API_CHANGES
	// Get list of priority numbers of conditions
	public Set<Integer> getPriorities();

	public void step(World w);
	
}
