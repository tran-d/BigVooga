package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Action;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

public class ActionTreeView extends TreeView<HBox> {

	private static final double TREE_VIEW_WIDTH = ActionConditionRow.TREE_VIEW_WIDTH;
	private static final double COLLAPSED_HEIGHT = ActionConditionRow.COLLAPSED_HEIGHT;
	private static final double EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT;
	private ActionCategoryTreeItem categoryAction;
	private ActionRow actionRow;
	private String categoryName;
	private String actionName;
	private Action action;
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ActionTreeView(ActionRow actionRow,Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.supplier = supplier;
		this.actionRow = actionRow;
		categoryAction = new ActionCategoryTreeItem(() -> changeRowTVSize(), supplier);
		setRoot(categoryAction);
		setPrefSize(TREE_VIEW_WIDTH, EXPANDED_HEIGHT);
		
	}

	public ActionTreeView(ActionRow actionRow, List<String> params, Action action,Supplier<List<AbstractSpriteObject>> supplier) {
		this(actionRow,supplier);
		
		this.supplier = supplier;
		this.action = action;
		this.categoryName = params.get(0);
		this.actionName = params.get(1);
		this.setRoot(new TreeItem<HBox>(new HBox(new Label("Category: "), new Label(categoryName),
				new Label(", Action: "), new Label(actionName))));
		setPrefSize(TREE_VIEW_WIDTH, COLLAPSED_HEIGHT);
	}

	public void setParameters(List<String> params, Action action) {
		this.action = action;
		this.categoryName = params.get(0);
		this.actionName = params.get(1);
	}

	public String getCategoryName() throws NullPointerException {
		if (categoryName != null)
			return categoryName;
		else {
			if (categoryAction.getSelectedCategory() != null)
				return categoryAction.getSelectedCategory();
			else
				throw new NullPointerException();
		}
	}

	public String getActionName() throws NullPointerException {
		if (actionName != null)
			return actionName;
		else {
			if (categoryAction.getSelectedAction() != null)
				return categoryAction.getSelectedAction();
			throw new NullPointerException();
		}
	}

	public Action getAction() {

		if (action != null)
			return action;
		else {
			try {
				Action action = categoryAction.extract();
				if (action == null)
					System.out.println("NULL ACTION");
				return action;
			} catch (NullPointerException e) {
				// showError(INVALID_INPUT_MESSAGE, ENTER_VALID_INPUT);
				throw e;
			} catch (NumberFormatException e) {
				throw e;
			}
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
