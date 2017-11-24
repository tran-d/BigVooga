package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class GameObject extends VariableContainer {
	
	
	private final String DEFAULT_TAG = "unnamed";
	private String name;
	private Set<String> tagSet;
	private Map<Condition, List<Action>> events;
	private double x, y, heading;

	public GameObject(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		tagSet = new HashSet<String>();
		x = 0;
		y = 0;
		heading = 0;
		events = new TreeMap<>();
	}
	public GameObject() {
		name = DEFAULT_TAG;
		tagSet = new HashSet<String>();
		x = 0;
		y = 0;
		heading = 0;
		events = new TreeMap<>();
	}
	
	
	public String getName() {
		return name;
	}

	public void addTag(String tag) {
		// TODO Auto-generated method stub
		tagSet.add(tag);
	}

	public boolean is(String tag) {
		// TODO Auto-generated method stub
		return tagSet.contains(tag);
	}

	public List<String> getTags() {
		// TODO Auto-generated method stub
		return new ArrayList<String>(tagSet);
	}

	public void setGlobal(String variableName, boolean global) {
		// TODO Auto-generated method stub

	}

	public void makeAllGlobal() {
		// TODO Auto-generated method stub

	}

	public void addToObjectList(GameObject o) {
		// TODO Auto-generated method stub

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

	public void setIntegerVariable(String name, int val) {}

	public void setBooleanVariable(String name, Boolean value) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * NEEDS COMPLETION
	 * @return BoundedImage
	 */
	
	public BoundedImage getImage()
	{
		//TODO return Object's image
		return null;
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
