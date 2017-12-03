package engine.operations;

import engine.GameObject;
import engine.Layer;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public interface Operation<T> {
	public T evaluate(GameObject asking, Layer world);
}
