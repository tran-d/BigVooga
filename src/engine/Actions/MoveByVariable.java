package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

public class MoveByVariable implements Action {

	String xSpeedVar, ySpeedVar;
	public MoveByVariable(String xSpeedVar, String ySpeedVar) {
		// TODO Auto-generated constructor stub
		this.xSpeedVar = xSpeedVar;
		this.ySpeedVar = ySpeedVar;
	}

	@Override
	public void execute(GameObject asking, Layer world) {
		// TODO Auto-generated method stub
		asking.setCoords(asking.getX() + asking.getDouble(xSpeedVar), asking.getY() + asking.getDouble(ySpeedVar));
	}

}
