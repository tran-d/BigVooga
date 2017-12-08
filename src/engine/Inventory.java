package engine;

import java.util.ArrayList;
import java.util.List;

import engine.operations.booleanops.BooleanOperation;
import engine.operations.booleanops.ScreenClickHeld;
import engine.sprite.BoundedImage;
import engine.sprite.Displayable;
import engine.utilities.collisions.BoundingPoint;

/**
 * 
 * @author Nikolas Bramblett, Aaron Paskin
 *
 */
public class Inventory implements Element{

	private List<Holdable> objects;
	private Holdable selected;
	private BoundedImage pane;
	
	private GameObject holder;

	public Inventory(GameObject holder) {
		objects = new ArrayList<Holdable>();
		this.holder = holder;
	}
	
	public Inventory(GameObject holder, BoundedImage pane) {
		this(holder);
		setPane(pane);
	}
	
	public void setPane(BoundedImage pane) {
		this.pane = pane;
	}

	public void addObject(Holdable newObject) {
		objects.add(newObject);

	}

	public List<Holdable> getFullInventory() {
		return new ArrayList<Holdable>(objects);

	}

	public void select(Holdable thatObject) {
		selected = thatObject;
	}

	@Override
	public Displayable getDisplayable() {
		//TODO Needs to return a properly formatted DisplayablePane
		return pane;
	}

	@Override
	public void step(int priorityNumber, Layer w, List<Runnable> runnables) {
		BooleanOperation screenClickHeld = new ScreenClickHeld();
		if(screenClickHeld.evaluate(new NullObject(), w) &&
			pane.checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(), w.getPlayerManager().getMouseXY().getY())) != null) {
			for(Holdable h : objects) {
				if(h.getBounds().checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(), w.getPlayerManager().getMouseXY().getY())) != null) {
					selected = h;
					selected.use(Holdable.SELECTED, holder, w);
				}
			}
		}
	}

}
