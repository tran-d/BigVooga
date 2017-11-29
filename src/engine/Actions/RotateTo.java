package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

public class RotateTo implements Action {

	private double newHeading;
	
	public RotateTo(double newHeading) {
		this.newHeading = newHeading;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setHeading(newHeading);
	}

}
