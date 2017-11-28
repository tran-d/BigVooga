package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import engine.World;

public class Collision extends Condition {

	private String tag;
	
	public Collision(int priorityNum, String tag) {
		this.priorityNum = priorityNum;
		this.tag = tag;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking, World world) {
		// TODO Auto-generated method stub
		return false;
	}

}
