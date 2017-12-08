package engine.Actions.movement;

import engine.Action;
import engine.GameObject;
import engine.Layer;
import engine.operations.gameobjectops.GameObjectOperation;
import engine.operations.vectorops.VectorOperation;
import javafx.geometry.Point2D;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Move implements Action {

	private VectorOperation increment;
	private GameObjectOperation object;
	
	public Move(GameObjectOperation object, VectorOperation increment) {
		this.object = object;
		this.increment = increment;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		Point2D vector = increment.evaluate(asking,world);
		GameObject obj = object.evaluate(asking, world);
		asking.setCoords(obj.getX() + vector.getX(), obj.getY() + vector.getY());
	}

}
