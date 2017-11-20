package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

/**
 * 
 * @author aaronpaskin
 *
 */
public class BeginStep extends Condition {

	public BeginStep() {
		priorityNum = 0;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		return true;
	}

}
