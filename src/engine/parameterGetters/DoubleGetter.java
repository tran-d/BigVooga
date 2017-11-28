package engine.parameterGetters;

import engine.GameObject;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class DoubleGetter extends ParameterGetter<Double> {

	public DoubleGetter(GameObjectGetter gameObject, String parameterName) {
		super(gameObject, parameterName);
	}

	@Override
	protected Double get(GameObject object, String parameterName) {
		return object.getDouble(parameterName);
	}

}
