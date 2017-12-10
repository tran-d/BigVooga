package engine;

import java.util.ArrayList;
import java.util.List;

import engine.operations.booleanops.BooleanOperation;
import engine.operations.booleanops.ScreenClickHeld;
import engine.operations.booleanops.ScreenClicked;
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
	private List<GameObject> scrollers;
	private BoundedImage pane;
	private GameObject holder;
	private int rowSpan, colSpan;
	private double x, y;
	private int startIndex;
	private String name;
	
	private Holdable selected;
	
	public static final int DEFAULT_ROWSPAN = 5;
	public static final int DEFAULT_COLSPAN = 5;

	public Inventory(GameObject holder, double x, double y) {
		this(holder, holder.getName() + "Inventory", x, y, DEFAULT_ROWSPAN, DEFAULT_COLSPAN, 0);
	}
	
	public Inventory(GameObject holder, String name, double x, double y, int rowSpan, int colSpan, int startIndex) {
		objects = new ArrayList<Holdable>();
		scrollers = new ArrayList<GameObject>();
		this.holder = holder;
		this.x = x;
		this.y = y;
		this.name = name;
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
				if(i >= objects.size()) {
					break;
				}
				row.add(objects.get(i).getDisplayable());
				i++;
			}
			ret.add(row);
		}
		return new DisplayablePane(pane, ret, rowSpan, colSpan);
	}

	@Override
	public void step(GameObjectEnvironment w) {
		BooleanOperation screenClicked = new ScreenClicked();
		if(screenClicked.evaluate(null, w) &&
			pane.checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(), w.getPlayerManager().getMouseXY().getY())) != null) {
			int i = 0;
			for(Holdable h : objects) {
				if(h.getDisplayable().checkCollision(new BoundingPoint(w.getPlayerManager().getMouseXY().getX(), w.getPlayerManager().getMouseXY().getY())) != null) {
					selected = h;
					h.select(holder, w);
					System.out.println("Holdable Selected: " + i);
				}
				i++;
			}
		}
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	public Holdable getSelected() {
		return selected;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String getName() {
		return name;
	}

}
