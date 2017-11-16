package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenericObject implements GameObject {

	private Set<String> tagSet;

	private double x, y;

	public GenericObject() {
		// TODO Auto-generated constructor stub
		tagSet = new HashSet<String>();
		x = 0;
		y = 0;
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
	public void setIntegerVariable(String name, int val) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDoubleVariable(String name, double val) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStringVariable(String name, String val) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIntegerVariable(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDoubleVariable(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getStringVariable(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addToObjectList(GameObject o) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConditionAction(Condition c, Action a) {
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
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}

}
