package engine.operations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import engine.VoogaException;

/**
 * Assumes names are unique
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class OperationFactory {
	private static final String PACKAGE = OperationFactory.class.getPackage().getName();
	private static final String BUNDLE_LOCATION = PACKAGE + ".Operations";
	private static final String DELIMITER = ", ";
	private static final int PACKAGE_LOCATION_INDEX = 0;
	private static final int FILE_NAME_INDEX = 1;
	private static final int CLASS_NAME_INDEX = 2;

	private Map<String, ResourceBundle> operationsByType;
	private Map<String, String> parTypeToBasicName;

	public OperationFactory() {
		populateMaps(ResourceBundle.getBundle(BUNDLE_LOCATION));
	}

	private void populateMaps(ResourceBundle bundle) {
		operationsByType = new HashMap<>();
		parTypeToBasicName = new HashMap<>();
		for (String BasicTypeName : Collections.list(bundle.getKeys())) {
			String[] properties = bundle.getString(BasicTypeName).split(DELIMITER);
			ResourceBundle ofType = ResourceBundle
					.getBundle(PACKAGE + "." + properties[PACKAGE_LOCATION_INDEX] + "." + properties[FILE_NAME_INDEX]);
			operationsByType.put(BasicTypeName, ofType);
			parTypeToBasicName.put(properties[CLASS_NAME_INDEX], BasicTypeName);
		}
	}

	public Operation<Object> makeOperation(String operationName, Object... parameters)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		return (Operation<Object>) Class.forName(getClassName(operationName)).getDeclaredConstructors()[0]
				.newInstance(parameters);
	}

	public List<String> getParameters(String operationName) throws SecurityException, ClassNotFoundException {
		List<String> types = new ArrayList<>();
		for (Class<?> parameterType : Class.forName(getClassName(operationName)).getDeclaredConstructors()[0]
				.getParameterTypes()) {
			types.add(parTypeToBasicName.get(parameterType.getName()));
		}
		return types;
	}

	private String getClassName(String operationName) {
		for (String key : operationsByType.keySet()) {
			if (operationsByType.get(key).containsKey(operationName))
				return operationsByType.get(key).getString(operationName);
		}
		throw new VoogaException("ClassNotFoundFor", operationName);
	}

	public List<String> getOperations(String operationType) {
		if (operationsByType.containsKey(operationType))
			return Collections.list(operationsByType.get(operationType).getKeys());
		return new ArrayList<>();
	}
}
