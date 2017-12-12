package authoring_actionconditions;

import authoring.ActionNameTreeItem;
import engine.Action;
import engine.Actions.ActionFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/**
 * Class representing an action row for sprites.
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ActionRow extends ActionConditionRow {

	private ActionFactory actionFactory;
	private TreeView<HBox> actionTreeView;
	private ActionCategoryTreeItem categoryAction;
	private ActionNameTreeItem actionName;

	public ActionRow(int ID, ActionVBox<ActionRow> ACVBox) {
		super(ID, ACVBox);
		actionFactory = new ActionFactory();
		this.setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);

		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize());
		actionTreeView = new TreeView<HBox>(categoryAction);
		actionTreeView.setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		this.getItems().addAll(actionTreeView);
	}
	
	public ActionRow(int ID,ActionVBox<ActionRow> ACVBox, TreeView<HBox> tv) {
		super(ID, ACVBox);
		this.getItems().addAll(tv);
	}

	/********************** PUBLIC METHODS ***********************/

	public TreeItem<HBox> getRootTreeItem() {
		return categoryAction;
	}

	public TreeView<HBox> getTreeView() {
		return actionTreeView;
	}

	public void changeRowTVSize() {
		if (categoryAction.isExpanded()) {
			this.setPrefHeight(EXPANDED_HEIGHT);
			actionTreeView.setPrefHeight(EXPANDED_HEIGHT);
		} else {
			this.setPrefHeight(COLLAPSED_HEIGHT);
			actionTreeView.setPrefHeight(COLLAPSED_HEIGHT);
		}
	}

	public Action getAction() {

		try {
			Action action = categoryAction.extract();
			if (action == null)
				System.out.println("NULL ACTION");
			return action;
		} catch (NullPointerException e) {
//			showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	private void addBuildActionButton(EventHandler<ActionEvent> handler) {
		Button buildActionButton = new Button(actionConditionVBoxResources.getString("BuildActionButton"));
		buildActionButton.setOnAction(handler);
		getItems().add(buildActionButton);
	}
}
