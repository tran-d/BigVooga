package engine.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import org.junit.Test;

import engine.GameObject;
import engine.GameObjectEnvironment;
import engine.Actions.ActionFactory;
import engine.Actions.changeObject.Create;
import engine.operations.OperationFactory;
import engine.operations.doubleops.Sum;
import engine.operations.stringops.StringOperation;

public class FactoryTesterJUnit {

	public void oldTest() {
		OperationFactory factory = new OperationFactory();
		;
		;
		StringOperation first = (a, b) -> "First";
		StringOperation second = (a, b) -> " and the second";
//		System.out.println(factory.makeOperation("Concatenate", first, second).evaluate(null, null));

		ActionFactory actFact = new ActionFactory();
		;
		;

		;
		;
	}

	@Test
	public void testActionsInActions() {
		OperationFactory factory = new OperationFactory();
		;
		assertTrue(factory.getOperations("Action").size() > 10);
		ActionFactory actFact = new ActionFactory();
		;
		assertTrue(actFact.getParameters("Do Times").contains("Action"));
		assertTrue(factory.getOperations(actFact.getParameters("Do Times").get(1)).contains("Do Times"));
	}

//	@Test
//	public void testParameterNames() {
//		assertEquals(Create.class.getConstructors()[0].getParameters()[1].getName(), "location");
//
//		OperationFactory factory = new OperationFactory();
//		ActionFactory actFact = new ActionFactory();
//		;
//	}

	@Test
	public void testAnnotations() {
		OperationFactory factory = new OperationFactory();
		;
	}
}
