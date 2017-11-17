package authoring_UI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ActionConditionRow extends HBox {

	private Label label;
	private PairList pairList;

	public ActionConditionRow(Label type,PairList variableValues) {
		super();
		label = type;
		pairList = variableValues;
	}
}
