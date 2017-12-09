package engine.operations.vectorops;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.operations.doubleops.DoubleOperation;
import javafx.geometry.Point2D;

public class BasicVector implements VectorOperation {

	private DoubleOperation x;
	private DoubleOperation y;

	public BasicVector(DoubleOperation x, DoubleOperation y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Point2D evaluate(GameObject asking, GameObjectEnvironment world) {
		return new Point2D(x.evaluate(asking, world), y.evaluate(asking, world));
	}

}
