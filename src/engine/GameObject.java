package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

//TODO need to add addToObjectList()? 

public abstract class GameObject extends VariableContainer {
	private final String DEFAULT_TAG = "unnamed";
	private String name;
	private Set<String> tagSet;
	private Map<Condition, List<Action>> events;
	private double x, y, heading;
	private Sprite currentSprite;
	private Map<String, Double> doubleVars;
	private Map<String, Boolean> booleanVars;
	private Map<String, String> stringVars;

	public GameObject() {
		name = DEFAULT_TAG;
		tagSet = new HashSet<String>();
		x = 0;
		y = 0;
		heading = 0;
		doubleVars = new HashMap<String, Double>();
		booleanVars = new HashMap<String, Boolean>();
		stringVars = new HashMap<String, String>();
		events = new TreeMap<>();
	}
	
	public GameObject(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addTag(String tag) {
		tagSet.add(tag);
	}

	public boolean is(String tag) {
		return tagSet.contains(tag);
	}

	public List<String> getTags() {
		return new ArrayList<String>(tagSet);
	}

	public void setGlobal(String variableName, World w) {
		GlobalVariables currentGlobals = w.getGlobalVars();
		if (doubleVars.containsKey(variableName)) {
			currentGlobals.putDouble(variableName, doubleVars.get(variableName));
		}
		if (stringVars.containsKey(variableName)) {
			currentGlobals.putString(variableName, stringVars.get(variableName));
		}
		if (booleanVars.containsKey(variableName)) {
			currentGlobals.putBoolean(variableName, booleanVars.get(variableName));
		}
	}

	public void makeAllGlobal(World w) {
		makeAllGlobalHelper(booleanVars.keySet(), w);
		makeAllGlobalHelper(doubleVars.keySet(), w);
		makeAllGlobalHelper(stringVars.keySet(), w);
	}
	
	private void makeAllGlobalHelper(Set<String> s, World w) {
		for (String key : s) {
			setGlobal(key, w);
		}
	}

	public void addConditionAction(Condition c, List<Action> a) {
		events.put(c, a);
	}

	public void step(World w) {
		for(Condition c : events.keySet()) {
			for(Action a : events.get(c)) {
				if(c.isTrue(this, w)) {
					a.execute(this, w);
				}
			}
		}
	}
	
	/**
	 * This is meant for the frontend to use for the purpose of placing a new instance of an object into the world.
	 * @param x, y
	 */
	public void setCoords(double x, double y) {
		// TODO Trigger listeners here
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setHeading(double newHeading) {
		heading = newHeading;
	}
	
	public double getHeading() {
		return heading;
	}

	public Set<Integer> getPriorities() {
		Set<Integer> priorities = new TreeSet<Integer>();
		for(Condition c : events.keySet()) {
			priorities.add(c.getPriority());
		}
		return priorities;
	}

	public void setIntegerVariable(String name, double value) {
		doubleVars.put(name, value);
	}

	public void setBooleanVariable(String name, Boolean value) {
		booleanVars.put(name, value);
	}
	
	public void setStringVariable(String name, String value) {
		stringVars.put(name, value);
	}
	
	public void setSprite(Sprite set) {
		currentSprite = set;
	}
	
	public void addParameter(Object o) {
		// TODO 
	}
	
	/**
	 * @return BoundedImage
	 */
	public BoundedImage getImage() {
		currentSprite.step();
		return currentSprite.getImage();
	}

	public GameObject clone() {
		GameObject copy = new GenericObject(name);
		copy.setCoords(x, y);
		copy.setHeading(heading);
		for(String tag: tagSet)
			copy.addTag(tag);
		for(String var : stringVars.keySet())
			copy.setStringVariable(var, stringVars.get(var));
		for(String var : doubleVars.keySet())
			copy.setDoubleVariable(var, doubleVars.get(var));
		for(String var : booleanVars.keySet())
			copy.setBooleanVariable(var, booleanVars.get(var));
		for(Condition c : events.keySet()) 
			copy.addConditionAction(c, new ArrayList<>(events.get(c)));
		return copy;
	}
}
