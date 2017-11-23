package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.VariableContainer;
import engine.World;

public class Move implements Action {

	private double xIncrement;
	private double yIncrement;
	
	public Move(double xIncrement, double yIncrement) {
		this.xIncrement = xIncrement;
		this.yIncrement = yIncrement;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		asking.setCoords(asking.getX() + xIncrement, asking.getY() + yIncrement);
	}

}
