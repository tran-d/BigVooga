package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.World;
import javafx.geometry.Point2D;

public class RemoveIntersection implements Action {
	
	@Override
	public void execute(GameObject asking, World world) {
		Point2D intVect = asking.getLastCollisionChecked().getOverlapVector();
		asking.setCoords(asking.getX()+intVect.getX(), asking.getY()+intVect.getY());
	}
	
}
