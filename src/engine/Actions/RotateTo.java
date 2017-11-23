package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.VariableContainer;
import engine.World;

public class RotateTo implements Action {

	private double newHeading;
	
	public RotateTo(double newHeading) {
		this.newHeading = newHeading;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		asking.setHeading(newHeading);
	}

}
