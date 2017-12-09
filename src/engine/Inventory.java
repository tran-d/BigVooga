package engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.operations.booleanops.BooleanOperation;
import engine.operations.booleanops.ScreenClickHeld;
import engine.sprite.BoundedImage;
import engine.sprite.Displayable;
import engine.sprite.DisplayableImage;
import engine.sprite.DisplayablePane;
import engine.utilities.collisions.BoundingPoint;

/**
 * 
 * @author Nikolas Bramblett, Aaron Paskin
 *
 */
public class Inventory implements Element{

	//TODO: Make scrollers
	
	private List<Holdable> objects;
	private BoundedImage pane;
	private GameObject holder;
	private int rowSpan, colSpan;
	private int startIndex;
	
	public static final int DEFAULT_ROWSPAN = 5;
	public static final int DEFAULT_COLSPAN = 5;

	public Inventory(GameObject holder) {
		this(holder, holder.getName() + "Inventory", DEFAULT_ROWSPAN, DEFAULT_COLSPAN, 0);
	}
	
	public Inventory(GameObject holder, String name, int rowSpan, int colSpan, int startIndex) {
		setPane(pane);
		objects = new ArrayList<Holdable>();
		this.holder = holder;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.startIndex = startIndex;
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

	@Override
	public Displayable getDisplayable() {
		List<List<DisplayableImage>> ret = new ArrayList<>();
		int i = startIndex;
		for(int r = 0; r < rowSpan; r++) {
			List<DisplayableImage> row = new ArrayList<>();
			for(int c = 0; c < colSpan; c++) {
				row.add(objects.get(i).getDisplayable());
				i++;
			}
			ret.add(row);
		}
		return new DisplayablePane(pane, ret);
	}

	@Override
	public void step(int priorityNumber, Layer w, List<Runnable> runnables) {
		BooleanOperation screenClickHeld = new ScreenClickHeld();
		if(screenClickHeld.evaluate(null, w) &&
			pane.checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(), w.getPlayerManager().getMouseXY().getY())) != null) {
			for(Holdable h : objects) {
				if(h.getDisplayable().checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(), w.getPlayerManager().getMouseXY().getY())) != null) {
					h.select(holder, w);
				}
			}
		}
	}
	
	@Override
	public Set<Integer> getPriorities() {
		Set<Integer> ret = new HashSet<>();
		ret.add(Integer.MAX_VALUE);
		return ret;
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

}
