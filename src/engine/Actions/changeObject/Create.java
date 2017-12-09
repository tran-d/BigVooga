package engine.Actions.changeObject;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.doubleops.DoubleOperation;
import engine.operations.stringops.StringOperation;
import engine.operations.vectorops.VectorOperation;
import javafx.geometry.Point2D;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Create implements Action {

	private StringOperation name;
	private DoubleOperation heading;
	private VectorOperation location;

	public Create(StringOperation name, VectorOperation location, DoubleOperation heading) {
		this.name = name;
		this.location = location;
		this.heading = heading;
	}

	@Override
	public void execute(GameObject asking, Layer world) {
		Point2D loc = location.evaluate(asking, world);
		world.addGameObject(name.evaluate(asking, world), loc.getX(), loc.getY(), heading.evaluate(asking, world));
	}

}
