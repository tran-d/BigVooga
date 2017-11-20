package engine.Actions;

import engine.Action;
import engine.VariableContainer;
import engine.World;

public class ChangeDouble implements Action {

	private String varName;
	private double newDouble;
	
	public ChangeDouble(String varName, double newDouble) {
		this.varName = varName;
		this.newDouble = newDouble;
	}
	
	@Override
	public void execute(VariableContainer asking, World world) {
		asking.setDoubleVariable(varName, newDouble);
	}
	
}
