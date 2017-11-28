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
	public void execute(VariableContainer asking, World world) {
		GameObject obj = (GameObject)asking;
		obj.setCoords(obj.getX() + xIncrement, obj.getY() + yIncrement);
	}

}
