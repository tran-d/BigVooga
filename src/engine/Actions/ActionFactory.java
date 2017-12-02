package engine.Actions;

import java.util.ArrayList;
import java.util.List;

import engine.Action;

//TODO ALL
public class ActionFactory {
	public List<String> getCategories(){
		return new ArrayList<>();
	}
	   
	public List<String> getActions(String category) {
		return new ArrayList<>();
	}

	public Action makeAction(String actionName, Object... parameters) {
		return new MoveTo(0, 0);
	}
	   
	public List<String> getParameters(String actionName) {
		return new ArrayList<>();
	}
}
