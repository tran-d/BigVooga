package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.Layer;

public class ChangeDouble implements Action {

	private String varName;
	private double newDouble;
	
	public ChangeDouble(String varName, double newDouble) {
		this.varName = varName;
		this.newDouble = newDouble;
	}
	
	@Override
	public void execute(GameObject asking, Layer world) {
		asking.setDoubleVariable(varName, newDouble);
	}
	
}
