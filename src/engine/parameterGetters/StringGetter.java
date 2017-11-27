package engine.parameterGetters;

import engine.GameObject;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class StringGetter extends ParameterGetter<String> {

	public StringGetter(GameObjectGetter gameObject, String parameterName) {
		super(gameObject, parameterName);
	}

	@Override
	protected String get(GameObject object, String parameterName) {
		return object.getString(parameterName);
	}

}