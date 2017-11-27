package engine.Actions;

import java.awt.geom.Point2D;

import engine.Action;
import engine.GameObject;
import engine.World;
import engine.utilities.collisions.CollisionEvent;

public class RemoveIntersection implements Action {
	
	@Override
	public void execute(GameObject asking, World world) {
		Point2D intVect = asking.getLastCollisionChecked().getOverlapVector();
		asking.setCoords(asking.getX()+intVect.getX(), asking.getY()+intVect.getY());
	}
	
}
