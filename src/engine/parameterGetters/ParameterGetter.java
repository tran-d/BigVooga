package engine.parameterGetters;

import engine.GameObject;
import engine.Layer;

/**
 * @author Ian Eldridge-Allegra
 *
 * @param <T> Type of parameter to get
 */
public abstract class ParameterGetter<T> {
	
	private GameObjectGetter gameObject;
	private String parameterName;
	
	public ParameterGetter(GameObjectGetter gameObject, String parameterName) {
		this.gameObject = gameObject;
		this.parameterName = parameterName;
	}
	
	public T get(GameObject asking, Layer world) {
		return get(gameObject.get(asking, world), parameterName);
	}
	
	protected abstract T get(GameObject object, String parameterName);
}
