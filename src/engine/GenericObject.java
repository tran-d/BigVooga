package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericObject extends VariableContainer implements GameObject {

	private String name;
	private Set<String> tagSet;
	private Map<Condition, List<Action>> events;
	private double x, y, heading;

	public GenericObject(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		tagSet = new HashSet<String>();
		x = 0;
		y = 0;
		heading = 0;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void addTag(String tag) {
		// TODO Auto-generated method stub
		tagSet.add(tag);
	}

	@Override
	public boolean is(String tag) {
		// TODO Auto-generated method stub
		return tagSet.contains(tag);
	}

	@Override
	public List<String> getTags() {
		// TODO Auto-generated method stub
		return new ArrayList<String>(tagSet);
	}

	@Override
	public void setGlobal(String variableName, boolean global) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeAllGlobal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addToObjectList(GameObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConditionAction(Condition c, List<Action> a) {
		// TODO Auto-generated method stub

	}

	@Override
	public void step(World w) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * This is meant for the frontend to use for the purpose of placing a new instance of an object into the world.
	 * @param x, y
	 */
	@Override
	public void setCoords(double x, double y) {
		// TODO Trigger listeners here
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public void setHeading(double newHeading) {
		heading = newHeading;
	}
	
	@Override
	public double getHeading() {
		return heading;
	}

	@Override
	public Set<Integer> getPriorities() {
		Set<Integer> priorities = new HashSet<Integer>();
		for(Condition c : events.keySet()) {
			priorities.add(c.getPriority());
		}
		return priorities;
	}

	@Override
	public void setIntegerVariable(String name, int val) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameObject clone() {
		GenericObject copy = new GenericObject(name);
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
