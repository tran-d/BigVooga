package engine;


/**
* Condition returns true, the Condition tells the GameObject to execute the actions associated with this condition.
*/
public abstract class Condition implements Comparable {
	
	public int priorityNum;
	
	public abstract boolean isTrue(GameObject asking, World world);
	
	public int getPriority() {
		return priorityNum;
	}
	
	@Override
	public int compareTo(Object o) {
		return priorityNum - ((Condition)o).getPriority();
	}
	
}
