package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;

public class SelfString implements StringOperation{

	private String stored;
	public SelfString(String s) {
		// TODO Auto-generated constructor stub
		stored = s;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		// TODO Auto-generated method stub
		return stored;
	}

}
