package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.World;

public class MoveTo implements Action {

	private double newX;
	private double newY;
	
	public MoveTo(double newX, double newY) {
		this.newX = newX;
		this.newY = newY;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		asking.setCoords(newX, newY);
	}

}
