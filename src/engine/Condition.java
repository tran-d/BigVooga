package engine;

/**
* Condition returns true, the Condition tells the GameObject to execute the actions associated with this condition.
*/
public abstract class Condition{
	
	public int priorityNum;
	
	public abstract boolean isTrue(VariableContainer asking);
	
	public int getPriority() {
		return priorityNum;
	}
	
}
