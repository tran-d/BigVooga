package engine.Conditions;

import engine.Condition;
import engine.GlobalVariables;
import engine.InputManager;
import engine.VariableContainer;
import engine.World;
import javafx.scene.input.MouseEvent;

/**
 * 
 * NOTE: implement ClickReleased when implementing this condition.
 * 
 * @author aaronpaskin
 *
 */
public class Clicked extends Condition{
	
	public Clicked(int priorityNum) {
		this.priorityNum = priorityNum;
	}
	
	/**
	 * Returns true if the primary mouse button is down but was not down in the previous step, i.e. when it is clicked.
	 */
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return !world.getInputManager().isPrevPrimaryButtonDown() && world.getInputManager().isPrimaryButtonDown();
	}

}
