package engine.operations.booleanops;

import engine.GameObject;
import engine.Layer;
import engine.operations.stringops.StringOperation;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

/**
 * 
 * @author aaronpaskin
 *
 */
public class Collision implements BooleanOperation {

	private StringOperation tag;
	
	public Collision(StringOperation tag) {
		this.tag = tag;
	}

	@Override
	public Boolean evaluate(GameObject asking, Layer world) {
		for(GameObject g : world.getWithTag(tag.evaluate(asking, world))) {
			//Ignore collision with self
			if(g == asking)
				continue;
			
			Point2D intersectionVector = g.getImage().checkCollision(asking.getImage());
			if(intersectionVector != null) {
				asking.setLastCollisionChecked(new CollisionEvent(g, intersectionVector));
				return true;
			}
		}
		return false;
	}
}
