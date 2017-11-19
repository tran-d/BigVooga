package engine.Conditions;

import engine.Condition;
import engine.VariableContainer;
import javafx.scene.input.KeyCode;

public class KeyHeld extends Condition {

	private KeyCode pressed;
	private String check;
	
	public KeyHeld(int priorityNum, KeyCode pressed, String check) {
		this.priorityNum = priorityNum;
		this.pressed = pressed;
		this.check = check;
	}
	
	@Override
	public boolean isTrue(VariableContainer asking) {
		return pressed.getName().equals(check);
	}
	
}
