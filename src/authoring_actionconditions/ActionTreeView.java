package authoring_actionconditions;

import engine.Action;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

public class ActionTreeView extends TreeView<HBox> {
	
	private static final double TREE_VIEW_WIDTH = 400;
	private static final double EXPANDED_HEIGHT = 300;
	private static final double COLLAPSED_HEIGHT = 25;
	private static final String ENTER_VALID_INPUT = "EnterValid";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	
	private ActionCategoryTreeItem categoryAction;
	private ActionRow actionRow;
	
	public ActionTreeView(ActionRow actionRow) {
		super();
		this.actionRow = actionRow;
		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize());
		setRoot(categoryAction);
		setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
	}
	
	public Action getAction() {

		try {
			Action action = categoryAction.extract();
			if (action == null)
				System.out.println("NULL ACTION");
			return action;
		} catch (Exception e) {
			showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
			return null;
		}
	}
	
	public void changeRowTVSize() {
		if (categoryAction.isExpanded()) {
			setPrefHeight(EXPANDED_HEIGHT);
			actionRow.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			setPrefHeight(COLLAPSED_HEIGHT);
			actionRow.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}
	
	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}

}
