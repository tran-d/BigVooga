package engine.operations;

import engine.Actions.ActionFactory;
import engine.operations.stringops.StringOperation;

public class FactoryTester {
	public static void main(String[] args) {
		OperationFactory factory = new OperationFactory();
		System.out.println(factory.getOperations("Boolean"));
		System.out.println(factory.getOperations("String"));
		StringOperation first = ()->"First";
		StringOperation second = ()->" and the second";
		System.out.println(factory.makeOperation("Concatenate", first, second).evaluate());
		
		ActionFactory actFact = new ActionFactory();
		System.out.println(actFact.getCategories());
		System.out.println(actFact.getActions(actFact.getCategories().get(0)));
	}
}
