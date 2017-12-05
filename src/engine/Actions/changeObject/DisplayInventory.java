package engine.Actions.changeObject;

import java.util.List;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.Actions.movement.MoveTo;
import engine.Actions.variableSetting.GiveInventory;
import engine.operations.doubleops.Difference;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.doubleops.Product;
import engine.operations.doubleops.Quotient;
import engine.operations.doubleops.Sum;
import engine.operations.doubleops.Value;
import engine.operations.gameobjectops.Get;

public class DisplayInventory implements Action {

	private GameObject inventoryDisplay;
	private List<GameObject> scrollers;
	
	public DisplayInventory(GameObject inventoryDisplay, List<GameObject> scrollers) {
		this.inventoryDisplay = inventoryDisplay;
		this.scrollers = scrollers;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		GiveInventory transfer = new GiveInventory(new Get(inventoryDisplay));
		transfer.execute(asking, world);
		world.addGameObject(inventoryDisplay);
		DoubleOperation inventoryX = new Value(inventoryDisplay.getX());
		DoubleOperation inventoryY = new Value(inventoryDisplay.getY());
		DoubleOperation inventoryWidth  = new Value(inventoryDisplay.getWidth());
		DoubleOperation inventoryHeight = new Value(inventoryDisplay.getHeight());
		DoubleOperation x0 = new Difference(inventoryX, inventoryWidth);
		DoubleOperation y0 = new Difference(inventoryY, inventoryHeight);
		DoubleOperation rowSpan = new Value(inventoryDisplay.getDouble("rowSpan"));	//TODO: make constant
		DoubleOperation colSpan = new Value(inventoryDisplay.getDouble("colSpan"));	//TODO: make constant
		DoubleOperation cellWidth = new Quotient(inventoryWidth, colSpan);
		DoubleOperation cellHeight = new Quotient(inventoryHeight, rowSpan);
		List<GameObject> inventory = (List)asking.getInventory().values();
		int i = (int)inventoryDisplay.getDouble("startIndex");						//TODO: make constant
		for(int r = 0; r < rowSpan.evaluate(asking, world); r++) {
			for(int c = 0; c < colSpan.evaluate(asking, world); c++) {
				GameObject o = inventory.get(i);
				world.addGameObject(o);
				MoveTo moveTo = new MoveTo(new Sum(x0, new Difference(new Product(cellWidth, new Value(c)), new Product(new Value(0.5), cellWidth))), new Sum(y0, new Difference(new Product(cellHeight, new Value(r)), new Product(new Value(0.5), cellHeight))));
				moveTo.execute(o, world);
				i++;
			}
		}
		for(GameObject o : scrollers)
			world.addGameObject(o);
	}

}
