package engine.operations;

import engine.Actions.ActionFactory;
import engine.operations.stringops.StringOperation;

public class FactoryTester {
	public static void main(String[] args) {
		OperationFactory factory = new OperationFactory();
		System.out.println(factory.getOperations("Boolean"));
		System.out.println(factory.getOperations("String"));
		StringOperation first = (a,b)->"First";
		StringOperation second = (a,b)->" and the second";
		System.out.println(factory.makeOperation("Concatenate", first, second).evaluate(null, null));
		
		ActionFactory actFact = new ActionFactory();
		System.out.println(actFact.getCategories());
		System.out.println(actFact.getActions(actFact.getCategories().get(0)));
		
		System.out.println(new ActionFactory().getParameters("Create Object"));
	}
}
