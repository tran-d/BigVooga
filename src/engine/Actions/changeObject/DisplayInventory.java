package engine.Actions.changeObject;

import java.util.Collection;
import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.Actions.movement.MoveTo;
import engine.operations.doubleops.Difference;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.doubleops.Magnitude;
import engine.operations.doubleops.Product;
import engine.operations.doubleops.Quotient;
import engine.operations.doubleops.Sum;
import engine.operations.doubleops.Value;
import engine.operations.gameobjectops.GameObjectOperation;

public class DisplayInventory implements Action {

	private GameObjectOperation inventoryDisplay;
	private List<GameObjectOperation> scrollers;
	private int startIndex;
	private DoubleOperation rowSpan;
	private DoubleOperation colSpan;
	
	public DisplayInventory(GameObjectOperation inventoryDisplay, List<GameObjectOperation> scrollers, int startIndex, DoubleOperation rows, DoubleOperation cols) {
		this.inventoryDisplay = inventoryDisplay;
		this.scrollers = scrollers;
		this.startIndex = startIndex;
		this.rowSpan = rows;
		this.colSpan = cols;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		GameObject inventoryPane = inventoryDisplay.evaluate(asking, world);
		world.addGameObject(inventoryPane);
		DoubleOperation inventoryX = new Value(inventoryPane.getX());
		DoubleOperation inventoryY = new Value(inventoryPane.getY());
		DoubleOperation inventoryWidth  = new Value(inventoryPane.getWidth());
		DoubleOperation inventoryHeight = new Value(inventoryPane.getHeight());
		DoubleOperation x0 = new Difference(inventoryX, inventoryWidth);
		DoubleOperation y0 = new Difference(inventoryY, inventoryHeight);
		DoubleOperation cellWidth = new Quotient(inventoryWidth, colSpan);
		DoubleOperation cellHeight = new Quotient(inventoryHeight, rowSpan);
		List<GameObject> inventory = (List)asking.getInventory().values();
		//place each game object in pane grid
		int i = startIndex;
		for(int r = 0; r < rowSpan.evaluate(asking, world); r++) {
			for(int c = 0; c < colSpan.evaluate(asking, world); c++) {
				GameObject o = inventory.get(i);
				world.addGameObject(o);
				MoveTo moveTo = new MoveTo(new Sum(x0, new Difference(new Product(cellWidth, new Value(c)), new Product(new Value(0.5), cellWidth))), new Sum(y0, new Difference(new Product(cellHeight, new Value(r)), new Product(new Value(0.5), cellHeight))));
				moveTo.execute(o, world);
				i++;
			}
		}
		for(GameObjectOperation o : scrollers) {
			world.addGameObject(o.evaluate(asking, world));
		}
	}

}
