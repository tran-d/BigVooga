package authoring_UI;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActionConditionManager extends HBox{
	
	private VBox conditions;
	private VBox actions;
	private List<HBox> conditionRows;
	private List<HBox> actionRows;
	private 
	
	public ActionConditionManager() {
		super();
		conditions = new VBox();
		actions = new VBox();
		getChildren().addAll(conditions,actions);
	}
	
	
}
