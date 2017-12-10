package authoring_actionconditions;

import authoring.ActionNameTreeItem;
import engine.Actions.ActionFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

public class ActionCategoryTreeItem extends TreeItem<HBox> {

	private static final double ROW_WIDTH = 800;
	private static final double TREE_VIEW_WIDTH = 600;
	private static final double EXPANDED_HEIGHT = 500;
	private static final double COLLAPSED_HEIGHT = 25;

	private static final String EMPTY_CHOICEBOX = "EmptyChoiceBox";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String DOUBLE_INPUT_MESSAGE = "EnterDouble";

	private ActionFactory actionFactory = new ActionFactory();
	private TreeItem<HBox> categoryAction = new TreeItem<HBox>();
	private ActionNameTreeItem actionName;
	private Runnable changeTreeViewSize;

	public ActionCategoryTreeItem(Runnable r) {
		changeTreeViewSize = r;
		this.makeActionCategoryTreeItem();
	}

	public TreeItem<HBox> getRootTreeItem() {
		return categoryAction;
	}

	public void extract() {
		try {
			actionName.extract();
		} catch (NullPointerException e) {
			showError(INVALID_INPUT_MESSAGE, EMPTY_CHOICEBOX);
		}
	}

	private TreeItem<HBox> makeActionCategoryTreeItem() {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action Category: "), makeActionCategoryChoiceBox(this));
		this.setValue(hb);
		this.setExpanded(true);
		this.expandedProperty().addListener(e -> changeTreeViewSize.run());
		return this;
	}

	private ChoiceBox<String> makeActionCategoryChoiceBox(TreeItem<HBox> categoryAction) {
		ObservableList<String> categories = FXCollections.observableList(actionFactory.getCategories());
		ChoiceBox<String> cb = new ChoiceBox<>(categories);
		System.out.println("cats: " + categories);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				categoryAction.getChildren().clear();
				actionName = new ActionNameTreeItem(categories.get(cb.getSelectionModel().getSelectedIndex()));
				categoryAction.getChildren().add(actionName);
			}
		});
		return cb;
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}
}
