package engine.operations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import engine.VoogaException;
import javafx.util.Pair;

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
	private static final int FILE_LOCATION_INDEX = 0;
	private static final int CLASS_NAME_INDEX = 1;

	private Map<String, ResourceBundle> operationsByType;
	private Map<String, String> parTypeToBasicName;

	public OperationFactory() {
		populateMaps();
	}

	public Operation<?> makeOperation(String operationName, Object... parameters) throws VoogaException {
		try {
			return (Operation<?>) getConstructor(operationName).newInstance(parameters);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| ClassNotFoundException e) {
			throw new VoogaException("CantMakeOperation", operationName);
		}
	}

	public List<String> getParameters(String operationName) throws VoogaException {
		List<String> types = new ArrayList<>();
		try {
			for (Class<?> parameterType : getConstructor(operationName).getParameterTypes()) {
				types.add(parTypeToBasicName.get(parameterType.getSimpleName()));
			}
		} catch (ClassNotFoundException e) {
			throw new VoogaException("ClassNotFoundFor", operationName);
		}
		return types;
	}

	public List<String> getOperations(String operationType) {
		if (operationsByType.containsKey(operationType))
			return Collections.list(operationsByType.get(operationType).getKeys());
		return new ArrayList<>();
	}

	private void populateMaps() {
		operationsByType = getOperationsByType();
		parTypeToBasicName = getParameterTypeMap();
	}

	private Constructor<?> getConstructor(String operationName) throws ClassNotFoundException {
		return Class.forName(getClassName(operationName)).getDeclaredConstructors()[0];
	}

	private String getClassName(String operationName) {
		for (String key : operationsByType.keySet()) {
			if (operationsByType.get(key).containsKey(operationName))
				return operationsByType.get(key).getString(operationName);
		}
		throw new VoogaException("ClassNotFoundFor", operationName);
	}
	
	public static Map<String, String> getParameterTypeMap() {
		return getFromBundle((key, properties)->new Pair<String, String>(properties[CLASS_NAME_INDEX], key));
	}
	
	public static Map<String, ResourceBundle> getOperationsByType() {
		return getFromBundle((key, properties)->new Pair<>(key, ResourceBundle.getBundle(PACKAGE + "." + properties[FILE_LOCATION_INDEX])));
	}
	
	private static <T> Map<String, T> getFromBundle(BiFunction<String, String[], Pair<String, T>> function) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_LOCATION);
		Map<String, T> parTypes = new HashMap<>();
		for (String basicTypeName : Collections.list(bundle.getKeys())) {
			String[] properties = bundle.getString(basicTypeName).split(DELIMITER);
			Pair<String, T> entry = function.apply(basicTypeName, properties);
			parTypes.put(entry.getKey(), entry.getValue());
		}
		return parTypes;
	}
}
