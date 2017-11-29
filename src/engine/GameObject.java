package engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import engine.sprite.BoundedImage;
import engine.sprite.Sprite;
import engine.utilities.collisions.CollisionEvent;

/**
 * 
 * @author Nikolas Bramblett, ...
 *
 */
public class GameObject extends VariableContainer {
	private final String DEFAULT_TAG = "unnamed";
	private String name;
	private Set<String> tagSet;
	private Map<Condition, List<Action>> events;
	private Sprite currentSprite;
	private CollisionEvent lastCollision;
	private double width = 200; //TODO Sizes
	private double height = 200; //TODO Sizes
	private int uniqueID;

	public static final String X_COR = "xCor";
	public static final String Y_COR = "yCor";
	public static final String HEADING = "heading";
	
	public GameObject() {
		name = DEFAULT_TAG;
		tagSet = new HashSet<String>();
		doubleVars = new HashMap<String, Double>();
		doubleVars.put(X_COR, 0.0);
		doubleVars.put(Y_COR, 0.0);
		doubleVars.put(HEADING, 0.0);
		booleanVars = new HashMap<String, Boolean>();
		stringVars = new HashMap<String, String>();
		events = new HashMap<>();
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

	public void setGlobal(String variableName, Layer w) {
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

	public void makeAllGlobal(Layer w) {
		makeAllGlobalHelper(booleanVars.keySet(), w);
		makeAllGlobalHelper(doubleVars.keySet(), w);
		makeAllGlobalHelper(stringVars.keySet(), w);
	}

	private void makeAllGlobalHelper(Set<String> s, Layer w) {
		for (String key : s) {
			setGlobal(key, w);
		}
	}

	public void addConditionAction(Condition c, List<Action> a) {
		events.put(c, a);
	}

	public void step(Layer w, int priorityNum, List<Runnable> runnables) {
		currentSprite.step();
		for (Condition c : events.keySet()) {
			if(c.getPriority() == priorityNum && c.isTrue(this, w)) {
				for (Action a : events.get(c)) {
					runnables.add(() -> a.execute(this, w));
				}
			}
		}
	}

	/**
	 * This is meant for the frontend to use for the purpose of placing a new
	 * instance of an object into the world.
	 * 
	 * @param x, y
	 */
	public void setCoords(double x, double y) {
		// TODO Trigger listeners here
		System.out.println("Enter SetCoords");
		System.out.println("Set x to " + x);
		doubleVars.put(X_COR, x);
		System.out.println("x = " + doubleVars.get(X_COR));
		doubleVars.put(Y_COR, y);
	}

	public double getX() {
		return doubleVars.get(X_COR);
	}

	public double getY() {
		return doubleVars.get(Y_COR);
	}

	public void setHeading(double newHeading) {
		doubleVars.put(HEADING, newHeading);
	}

	public double getHeading() {
		return doubleVars.get(HEADING);
	}

	public Set<Integer> getPriorities() {
		Set<Integer> priorities = new TreeSet<Integer>();
		for (Condition c : events.keySet()) {
			priorities.add(c.getPriority());
		}
		return priorities;
	}

	public void setDoubleVariable(String name, Double value) {
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

	public void addParameter(String name, Object o) throws VoogaException {
		try {
			getClass().getDeclaredMethod(
					ResourceBundle.getBundle("engine.TypeRecovery").getString(o.getClass().getSimpleName()),
					String.class, o.getClass()).invoke(this, name, o);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new VoogaException("AddPar", name, o.getClass());
		}
	}

	/**
	 * @return BoundedImage
	 */
	public BoundedImage getImage() {
		//TODO width and height?
		BoundedImage result = currentSprite.getImage();
		result.setPosition(doubleVars.get(X_COR), doubleVars.get(Y_COR));
		result.setHeading(doubleVars.get(HEADING));
		result.setSize(width, height);
		return result;
	}

	public GameObject clone() {
		GameObject copy = new GameObject(name);
		copy.setCoords(doubleVars.get(X_COR), doubleVars.get(Y_COR));
		copy.setHeading(doubleVars.get(HEADING));
		copy.currentSprite = currentSprite.clone();
		for (String tag : tagSet)
			copy.addTag(tag);
		for (String var : stringVars.keySet())
			copy.setStringVariable(var, stringVars.get(var));
		for (String var : doubleVars.keySet())
			copy.setDoubleVariable(var, doubleVars.get(var));
		for (String var : booleanVars.keySet())
			copy.setBooleanVariable(var, booleanVars.get(var));
		for (Condition c : events.keySet())
			copy.addConditionAction(c, new ArrayList<>(events.get(c)));
		return copy;
	}
	
	public CollisionEvent getLastCollisionChecked() {
		return lastCollision;
	}

	public void setLastCollisionChecked(CollisionEvent collisionEvent) {
		lastCollision = collisionEvent;
	}

	/**
	 * @return the uniqueID
	 */
	public int getUniqueID() {
		return uniqueID;
	}

	/**
	 * @param uniqueID the uniqueID to set
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}
}
