package engine.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;
import engine.utilities.collisions.CollisionEvent;
import javafx.geometry.Point2D;

public class Collision extends Condition {

	private String tag;
	
	public Collision(int priorityNum, String tag) {
		this.priorityNum = priorityNum;
		this.tag = tag;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		for(GameObject g : world.getWithTag(tag)) {
			Point2D intersectionVector = g.getImage().checkCollision(asking.getImage());
			if(intersectionVector != null) {
				asking.setLastCollisionChecked(new CollisionEvent(g, intersectionVector));
				return true;
			}
		}
		return false;
	}

}
