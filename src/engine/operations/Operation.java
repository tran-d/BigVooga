package engine.operations;

import engine.GameObject;
import engine.GameObjectEnvironment;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public interface Operation<T> {
	public T evaluate(GameObject asking, GameObjectEnvironment world);
}
