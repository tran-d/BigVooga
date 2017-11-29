package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.World;
import engine.utilities.collisions.BoundingPoint;

/**
 * Checks if a click is being held on an object.
 * 
 * @author aaronpaskin
 *
 */
public class ObjectClickHeld extends Condition {

	public ObjectClickHeld(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	@Override
	public boolean isTrue(GameObject asking, World world) {
		Condition screenClickHeld = new ScreenClickHeld(0);
		return screenClickHeld.isTrue(asking, world) && 
//				world.getPlayerManager().getClickX() > asking.getImage().getX() - 0.5 * asking.getImage().getWidth() &&
//				world.getPlayerManager().getClickX() < asking.getImage().getX() + 0.5 * asking.getImage().getWidth() &&
//				world.getPlayerManager().getClickY() > asking.getImage().getY() - 0.5 * asking.getImage().getHeight() &&
//				world.getPlayerManager().getClickY() < asking.getImage().getY() + 0.5 * asking.getImage().getHeight() &&
				asking.getImage().checkCollision(new BoundingPoint(world.getPlayerManager().getClickX(), world.getPlayerManager().getClickY())) != null;
	}

}
