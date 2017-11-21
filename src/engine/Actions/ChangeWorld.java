package engine.Actions;

import engine.Action;
import engine.VariableContainer;
import engine.World;

public class ChangeWorld implements Action {

	private World newWorld;
	
	public ChangeWorld(World newWorld) {
		this.newWorld = newWorld;
	}
	
	@Override
	public void execute(VariableContainer asking, World world) {
		//pass player object into newWorld
		world.setNextWorld(newWorld);
	}

}
