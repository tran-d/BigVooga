package engine.operations.vectorops;

import engine.GameObject;
import engine.Layer;
import javafx.geometry.Point2D;

public class Heading implements VectorOperation {

	@Override
	public Point2D evaluate(GameObject asking, Layer world) {
		return new Point2D(Math.cos(Math.toRadians(asking.getHeading())), Math.sin(Math.toRadians(asking.getHeading())));
	}

}
