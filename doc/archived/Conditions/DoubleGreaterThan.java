package engine.archived.Conditions;

import engine.Condition;
import engine.GameObject;
import engine.Layer;

/**
 * 
 * @author aaronpaskin
 *
 */
public class DoubleGreaterThan extends Condition {

	private String varName;
	private double check;
	
	public DoubleGreaterThan(int priorityNum, String varName, double check) {
		this.priorityNum = priorityNum;
		this.varName = varName;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(GameObject asking, Layer world) {
		return asking.getDouble(varName) > check;
	}
	
}
