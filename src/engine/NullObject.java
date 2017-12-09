package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import engine.sprite.BoundedImage;
import engine.sprite.Displayable;
import engine.sprite.DisplayableText;
import engine.sprite.Sprite;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

/**
 * Default return for GameObjectOperations
 * 
 * @author aaronpaskin
 *
 */
public class NullObject extends GameObject {

	
	public String getName() {
		return "";
	}

	public void addTag(String tag) {
	}

	public boolean is(String tag) {
		return false;
	}

	public List<String> getTags() {
		return new ArrayList<String>();
	}

	public void setGlobal(String variableName, Layer w) {
	}

	public void makeAllGlobal(Layer w) {
	}

	public void addConditionAction(Condition c, List<Action> a) {
	}

	public void step(Layer w, int priorityNum, List<Runnable> runnables) {
	}

	public void setCoords(double x, double y) {
	}

	public void setLocation(Point2D loc) {
	}

	public double getX() {
		return 0;
	}

	public double getY() {
		return 0;
	}

	public void setHeading(double newHeading) {
	}

	public double getHeading() {
		return 0;
	}

	public Set<Integer> getPriorities() {
		return new TreeSet<>();
	}

	public void setSprite(Sprite set) {
	}

	public void addParameter(String name, Object o) throws VoogaException {
	}

	/**
	 * Returns the current image of this Object.
	 * 
	 * @return BoundedImage
	 */
	public BoundedImage getBounds() {
		return new BoundedImage("");
	}

	public Displayable getDisplayable() {
		return getBounds();
	}

	/**
	 * Creates a new instance of this game object, which has the same values (but
	 * can take new values)
	 */
	public GameObject clone() {
		return this;
	}

	public CollisionEvent getLastCollisionChecked() {
		return new CollisionEvent(this, new Point2D(0,0));
	}

	public void setLastCollisionChecked(CollisionEvent collisionEvent) {
	}

	/**
	 * @return the uniqueID
	 */
	public int getUniqueID() {
		return -1;
	}

	/**
	 * @param uniqueID
	 *            the uniqueID to set
	 */
	public void setUniqueID(int uniqueID) {
	}

	public void setSize(double width, double height) {
	}

	public double getWidth() {
		return 0;
	}

	public double getHeight() {
		return 0;
	}

	public Inventory getInventory() {
		return null;
	}

	public void addToInventory(Element o) {
	}

	public void setDialogue(DisplayableText text) {
	}

	public void setDialogue(String s) {
	}

}
