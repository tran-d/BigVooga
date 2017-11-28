package engine;


/**
* Condition returns true, the Condition tells the GameObject to execute the actions associated with this condition.
*/
public abstract class Condition implements Comparable<Condition> {
	
	public int priorityNum;
	
	public abstract boolean isTrue(GameObject asking, World world);
	
	public int getPriority() {
		return priorityNum;
	}
	
	@Override
	public int compareTo(Condition o) {
		return priorityNum - o.getPriority();
	}
	
}
