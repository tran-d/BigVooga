package engine.operations.stringops;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Concatenate implements StringOperation{

	private StringOperation first;
	private StringOperation second;

	public Concatenate(StringOperation first, StringOperation second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String evaluate(GameObject asking, GameObjectEnvironment world) {
		return first.evaluate(asking, world)+second.evaluate(asking, world);
	}
}
