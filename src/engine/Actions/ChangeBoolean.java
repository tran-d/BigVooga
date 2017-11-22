package engine.Actions;

import engine.Action;
import engine.GameObject;
import engine.World;

public class ChangeBoolean implements Action {

	private String varName;
	private boolean newBoolean;
	
	public ChangeBoolean(String varName, Boolean newBoolean) {
		this.varName = varName;
		this.newBoolean = newBoolean;
	}
	
	@Override
	public void execute(GameObject asking, World world) {
		asking.setBooleanVariable(varName, newBoolean);
	}
	
}
