package engine.operations;

import java.lang.reflect.InvocationTargetException;

import engine.operations.stringops.StringOperation;

public class FactoryTester {
	public static void main(String[] args) {
		OperationFactory factory = new OperationFactory();
		System.out.println(factory.getOperations("Boolean"));
		System.out.println(factory.getOperations("String"));
		StringOperation first = ()->"First";
		StringOperation second = ()->" and the second";
		try {
			System.out.println(factory.makeOperation("Concatenate", first, second).evaluate());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			System.out.println(first.getClass().getName());
			e.printStackTrace();
		}
	}
}
