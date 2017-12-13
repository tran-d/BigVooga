package engine;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import engine.sprite.Sprite;
import javafx.geometry.Point2D;

/**
 * Holds variables of different types with String names. GameObject and
 * GlobalVariables extend this.
 * 
 * @author Aaron Paskin
 *
 */
public abstract class VariableContainer {

	private static final boolean DEFAULT_BOOLEAN = false;
	private static final String DEFAULT_STRING = "";
	private static final int DEFAULT_DOUBLE = 0;

	protected Map<String, Double> doubleVars;
	protected Map<String, String> stringVars;
	protected Map<String, Boolean> booleanVars;

	protected Sprite sprite;
	protected Set<String> tagSet;

	public VariableContainer() {
		doubleVars = new HashMap<String, Double>();
		stringVars = new HashMap<String, String>();
		booleanVars = new HashMap<String, Boolean>();
	}

	public Map<String, Double> getAllDoubleVars() {
		return doubleVars;
	}

	public Map<String, String> getAllStringVars() {
		return stringVars;
	}

	public Map<String, Boolean> getAllBooleanVars() {
		return booleanVars;
	}

	public double getDouble(String key) {
		if (doubleVars.containsKey(key))
			return doubleVars.get(key);
		return DEFAULT_DOUBLE;
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

	public String getString(String key) {
		if (stringVars.containsKey(key))
			return stringVars.get(key);
		return DEFAULT_STRING;
	}

	public boolean getBoolean(String key) {
		if (booleanVars.containsKey(key))
			return booleanVars.get(key);
		return DEFAULT_BOOLEAN;
	}

	public void setDoubleVariable(String name, double val) {
		doubleVars.put(name, val);
	}

	public void setStringVariable(String name, String val) {
		stringVars.put(name, val);
	}

	public void setBooleanVariable(String name, boolean val) {
		booleanVars.put(name, val);
	}

	public Set<String> getTags() {
		return new HashSet<String>(tagSet);
	}

	public void setSprite(Sprite set) {
		sprite = set;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void cloneHelp(VariableContainer copy) {
		for (String tag : tagSet)
			copy.addTag(tag);
		for (String var : stringVars.keySet())
			copy.setStringVariable(var, stringVars.get(var));
		for (String var : doubleVars.keySet())
			copy.setDoubleVariable(var, doubleVars.get(var));
		for (String var : booleanVars.keySet())
			copy.setBooleanVariable(var, booleanVars.get(var));

		copy.sprite = sprite.clone();
		copy.tagSet = new HashSet<String>(tagSet);
	}

	public void addTag(String tag) {
		tagSet.add(tag);
	}

	public boolean is(String tag) {
		return tagSet.contains(tag);
	}

}
