package engine.parameterGetters;

import engine.GameObject;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class BooleanGetter extends ParameterGetter<Boolean> {

	public BooleanGetter(GameObjectGetter gameObject, String parameterName) {
		super(gameObject, parameterName);
	}

	@Override
	protected Boolean get(GameObject object, String parameterName) {
		return object.getBoolean(parameterName);
	}
}
