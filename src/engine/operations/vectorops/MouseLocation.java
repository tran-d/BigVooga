package engine.operations.vectorops;

import controller.player.PlayerManager;
import engine.GameObject;
import engine.Layer;
import javafx.geometry.Point2D;

public class MouseLocation implements VectorOperation {

	public MouseLocation() {}
	
	@Override
	public Point2D evaluate(GameObject asking, Layer world) {
		PlayerManager pm = world.getPlayerManager();
		return pm.getMouseXY();
	}

}
