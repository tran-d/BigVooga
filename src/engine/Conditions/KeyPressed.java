package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import javafx.scene.input.KeyCode;

public class KeyPressed extends Condition {

	private KeyCode pressed;
	private String check;
	
	public KeyPressed(int priorityNum, KeyCode pressed, String check) {
		this.priorityNum = priorityNum;
		this.pressed = pressed;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking) {
		return pressed.getName().equals(check);
	}
	
}
