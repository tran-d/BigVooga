package authoring_actionconditions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Class representing an action row for sprites.
 * 
 * @author DavidTran
 *
 */
public class ActionRow extends ActionConditionRow {

	public ActionRow(int ID, String label, String selectorLabel,String selectedAction, ActionVBox<ActionRow> ACVBox) {
		super(ID, label, selectorLabel, selectedAction, ACVBox);
		addBuildActionButton(e -> openBuildWindow());
	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}

	private void openBuildWindow() {
		// if (view == null && actionOptions.getSelected() != null)
		// view = new BuildActionView(ACVBox, (ActionConditionRow)
		// ACVBox.getChildren().get(labelInt - 1));
	}
}
