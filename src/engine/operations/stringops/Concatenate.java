package engine.operations.stringops;

import engine.GameObject;
import engine.Layer;

public class Concatenate implements StringOperation{

	private StringOperation first;
	private StringOperation second;

	public Concatenate(StringOperation first, StringOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String evaluate(GameObject asking, Layer world) {
		return first.evaluate(asking, world)+second.evaluate(asking, world);
	}
}
