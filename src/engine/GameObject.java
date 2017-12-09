package engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import engine.sprite.BoundedImage;
import engine.sprite.CompositeImage;
import engine.sprite.Displayable;
import engine.sprite.DisplayableImage;
import engine.sprite.DisplayableText;
import engine.sprite.Sprite;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

/**
 * The core of the game. Everything visible will be a GameObject. GameObjects
 * have 2 paradigms: Variables are just named quantities unique to each instance
 * of an object, such as names or coordinates.
 * 
 * Step() calls the Object's conditions and actions, which evaluate and modify
 * its current state based on the conditions of the game.
 * 
 * @author Nikolas Bramblett, Ian Eldridge-Allegra
 *
 */
public class GameObject extends VariableContainer implements Element {

	private static final double DEFAULT_SIZE = 200;
	private String name;
	private Set<String> tagSet;
	private Map<Condition, List<Action>> events;
	private Sprite currentSprite;
	private CollisionEvent lastCollision;
	private double width = DEFAULT_SIZE;
	private double height = DEFAULT_SIZE;
	private int uniqueID;

	private Inventory inventory;
	private DisplayableText dialogueHandler;

	private static final String DEFAULT_NAME = "unnamed";
	private static final String DEFAULT_TAG = "default";

	private double x, y, heading;

	public GameObject() {
		this(DEFAULT_NAME);
	}

	public GameObject(String name) {
		tagSet = new HashSet<String>();
		doubleVars = new HashMap<String, Double>();
		booleanVars = new HashMap<String, Boolean>();
		stringVars = new HashMap<String, String>();
		events = new HashMap<>();
		this.name = name;
		tagSet.add(name);
		tagSet.add(DEFAULT_TAG);
		inventory = new Inventory(this);
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

	public void addConditionAction(Condition c, List<Action> a) {
		events.put(c, a);
	}

	/**
	 * Steps animation and checks/executes events in order of priority
	 */
	@Override
	public void step(int priorityNum, Layer w, List<Runnable> runnables) {
		currentSprite.step();
		for (Condition c : events.keySet()) {
			if (c.getPriority() == priorityNum && c.isTrue(this, w)) {
				for (Action a : events.get(c)) {
					runnables.add(() -> a.execute(this, w));
				}
			}
		}
	}

	/**
	 * Setter for x and y Coordinates
	 * 
	 * @param x, y
	 */
	public void setCoords(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setLocation(Point2D loc) {
		setCoords(loc.getX(), loc.getY());
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

	/**
	 * Compiles all priorities of Conditions into an iterable set. Used by Layer to
	 * call Events in proper order.
	 * 
	 * @return {Set<Integer>} priorities
	 */
	@Override
	public Set<Integer> getPriorities() {
		Set<Integer> priorities = new TreeSet<Integer>();
		for (Condition c : events.keySet()) {
			priorities.add(c.getPriority());
		}
		return priorities;
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
	 * Returns the current BoundedImage of this Object.
	 * 
	 * @return BoundedImage
	 */
	public BoundedImage getBounds() {
		BoundedImage result = currentSprite.getImage();
		result.setPosition(x, y);
		result.setHeading(heading);
		result.setSize(width, height);
		return result;
	}
	
	@Override
	public Displayable getDisplayable() {
		if (dialogueHandler == null)
			return getBounds();
		dialogueHandler.setHeading(getHeading());
		dialogueHandler.setHeight(getHeight());
		dialogueHandler.setWidth(getWidth());
		dialogueHandler.setX(getX());
		dialogueHandler.setY(getY());
		return new CompositeImage(getBounds(), dialogueHandler);
	}

	/**
	 * Creates a new instance of this game object, which has the same values (but
	 * can take new values)
	 */
	public GameObject clone() {
		GameObject copy = new GameObject(name);
		copy.setCoords(x, y);
		copy.setHeading(heading);
		copy.currentSprite = currentSprite.clone();
		copy.setSize(width, height);
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
	 * @param uniqueID
	 *            the uniqueID to set
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public void addToInventory(Holdable o) {
		inventory.addObject(o);
	}

	public void setDialogue(DisplayableText text) {
		dialogueHandler = text;
	}

	public void setDialogue(String s) {
		if (dialogueHandler == null)
			dialogueHandler = DisplayableText.DEFAULT;
		dialogueHandler = dialogueHandler.getWithMessage(s);
	}
}
