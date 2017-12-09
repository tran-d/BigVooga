package engine.operations;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.Actions.ActionFactory;
import engine.operations.stringops.StringOperation;

public class FactoryTesterJUnit {

	public void oldTest() {
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
		System.out.println(actFact.getParameters(actFact.getActions(actFact.getCategories().get(0)).get(0)));
	}
	
	@Test
	public void testActionsInActions() {
		OperationFactory factory = new OperationFactory();
		System.out.println(factory.getOperations("Action"));
		assertTrue(factory.getOperations("Action").size()>10);
		ActionFactory actFact = new ActionFactory();
		System.out.println(actFact.getActions("Loops"));
		assertTrue(actFact.getParameters("Do Times").contains("Action"));
		assertTrue(factory.getOperations(actFact.getParameters("Do Times").get(1)).contains("Do Times"));
	}

}
