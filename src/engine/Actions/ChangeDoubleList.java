package engine.Actions;

import java.util.List;

import engine.Action;
import engine.VariableContainer;
import engine.World;

public class ChangeDoubleList implements Action {

	private String varName;
	private List<Double> newDoubleList;
	
	public ChangeDoubleList(String varName, List<Double> newDoubleList) {
		this.varName = varName;
		this.newDoubleList = newDoubleList;
	}
	
	@Override
	public void execute(VariableContainer asking, World world) {
		asking.setDoubleListVariable(varName, newDoubleList);
	}
	
}
