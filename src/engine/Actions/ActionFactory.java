package engine.Actions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import engine.Action;
import engine.VoogaException;
import engine.operations.OperationFactory;
import engine.operations.VoogaAnnotation;
import engine.operations.VoogaParameter;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class ActionFactory {

	private static final String PACKAGE_NAME = ActionFactory.class.getPackage().getName();
	private static final String BUNDLE_LOCATION = PACKAGE_NAME + ".Actions";

	private Map<String, ResourceBundle> categoryBundles;
	private Map<String, String> operationTypeMap;

	public ActionFactory() {
		populateCategoryMap();
		operationTypeMap = OperationFactory.getParameterTypeMap();
	}

	private void populateCategoryMap() {
		categoryBundles = new HashMap<>();
		ResourceBundle bundleLocations = ResourceBundle.getBundle(BUNDLE_LOCATION);
		for (String category : bundleLocations.keySet()) {
			categoryBundles.put(category,
					ResourceBundle.getBundle(PACKAGE_NAME + "." + bundleLocations.getString(category)));
		}
	}

	public List<String> getCategories() {
		return new ArrayList<>(categoryBundles.keySet());
	}

	public List<String> getActions(String category) {
		return Collections.list(categoryBundles.get(category).getKeys());
	}

	public Action makeAction(String actionName, Object... parameters) {
		;
		try {
			return (Action) getConstructor(actionName).newInstance(parameters);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException e) {
			throw new VoogaException("ActionNotFound", actionName);
		}
	}

	@Deprecated
	public List<String> getParameters(String actionName) {
		List<String> types = new ArrayList<>();
		try {
			for (Class<?> parameterType : getConstructor(actionName).getParameterTypes())
				types.add(operationTypeMap.get(parameterType.getSimpleName()));
		} catch (ClassNotFoundException e) {
			throw new VoogaException("ActionNotFound", actionName);
		}
		return types;
	}

	public List<VoogaParameter> getParametersWithNames(String actionName) {
		List<VoogaParameter> types = new ArrayList<>();
		try {
			for (Parameter parameter : getConstructor(actionName).getParameters()) {
				types.add(new VoogaParameter(parameter.getAnnotation(VoogaAnnotation.class).name(),
						parameter.getAnnotation(VoogaAnnotation.class).type()));
			}
		} catch (ClassNotFoundException e) {
			throw new VoogaException("ActionNotFound", actionName);
		}
		return types;
	}

	private Constructor<?> getConstructor(String operationName) throws ClassNotFoundException {
		return Class.forName(getClassName(operationName)).getDeclaredConstructors()[0];
	}

	private String getClassName(String actionName) {
		for (String key : categoryBundles.keySet()) {
			if (categoryBundles.get(key).containsKey(actionName))
				return categoryBundles.get(key).getString(actionName);
		}
		throw new VoogaException("ClassNotFoundFor", actionName);
	}
}
