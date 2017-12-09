package engine.operations.stringops;

import engine.GameObject;
import engine.Layer;

public class SelfString implements StringOperation{

	private String stored;
	public SelfString(String s) {
		// TODO Auto-generated constructor stub
		stored = s;
	}

	@Override
	public String evaluate(GameObject asking, Layer world) {
		// TODO Auto-generated method stub
		return stored;
	}

}
