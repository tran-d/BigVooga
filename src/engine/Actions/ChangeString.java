package engine.Actions;

import engine.Action;
import engine.VariableContainer;
import engine.World;

public class ChangeString implements Action {

	private String varName;
	private String newString;
	
	public ChangeString(String varName, String newString) {
		this.varName = varName;
		this.newString = newString;
	}
	
	@Override
	public void execute(VariableContainer asking, World world) {
		asking.setStringVariable(varName, newString);
	}
	
}
