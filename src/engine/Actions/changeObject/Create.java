package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Create implements Action {

	private StringOperation name;
	DoubleOperation x, y, heading;
	
	public Create(StringOperation name, DoubleOperation x, DoubleOperation y, DoubleOperation heading) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		world.addGameObject(name.evaluate(asking, world), x.evaluate(asking, world), y.evaluate(asking, world), heading.evaluate(asking, world));
	}
	
}
