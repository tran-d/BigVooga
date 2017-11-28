package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.World;

public class MoveByVariable implements Action {

	String xSpeed, ySpeed;
	public MoveByVariable(String xSpeedVar, String ySpeedVar) {
		// TODO Auto-generated constructor stub
		xSpeed = xSpeedVar;
		ySpeed = ySpeedVar;
	}

	@Override
	public void execute(GameObject asking, World world) {
		// TODO Auto-generated method stub
		asking.setCoords(asking.getX() + asking.getDouble(xSpeed), asking.getY() + asking.getDouble(ySpeed));
	}

}
