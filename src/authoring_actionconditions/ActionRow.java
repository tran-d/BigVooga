package authoring_actionconditions;

import engine.Actions.ActionFactory;
import engine.operations.OperationFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

/**
 * Class representing an action row for sprites.
 * 
 * @author DavidTran
 *
 */
public class ActionRow extends ActionConditionRow {

	private ActionFactory actionFactory;
	private TreeView<HBox> actionTree;

	private TreeItem<HBox> categoryAction = new TreeItem<HBox>();
	private TreeItem<HBox> actionAction = new TreeItem<HBox>();
	private TreeItem<HBox> parameterAction = new TreeItem<HBox>();

	private TreeItem<HBox> categoryOperation = new TreeItem<HBox>();
	private TreeItem<HBox> actionOperation = new TreeItem<HBox>();
	private TreeItem<HBox> parameterOperation = new TreeItem<HBox>();

	private TreeView<HBox> actionTreeView;
	private TreeView<HBox> operationTreeView;
	private OperationFactory operationFactory;

	public ActionRow(int ID, String label, String selectorLabel, boolean isConditionRow,
			ObservableList<Integer> newActionOptions, ActionConditionVBox ACVBox) {

		super(ID, label, selectorLabel, newActionOptions, ACVBox);

		// addBuildActionButton(e -> openBuildWindow());

		actionFactory = new ActionFactory();
		operationFactory = new OperationFactory();

		// this.getItems().add(makeActionChoiceBox("Movement"));
		// actionTree = makeTreeView(makeActionChoiceBox("Movement"));
		// this.getItems().add(actionTree);
		actionTreeView = makeActionTreeView();
		operationTreeView = makeOperationTreeView();

		this.getItems().addAll(actionTreeView, operationTreeView);
	}

	private TreeView<HBox> makeActionTreeView() {
		categoryAction = makeActionCategoryTreeItem();
		TreeView<HBox> tv = new TreeView<HBox>(categoryAction);
		return tv;
	}

	private TreeItem<HBox> makeActionCategoryTreeItem() {
		HBox hb = new HBox();
		hb.getChildren().add(makeActionCategoryChoiceBox());
		TreeItem<HBox> ti = new TreeItem<HBox>(hb);
		ti.setExpanded(true);
		return ti;
	}

	private ChoiceBox<String> makeActionCategoryChoiceBox() {
		ObservableList<String> categories = FXCollections.observableList(actionFactory.getCategories());
		ChoiceBox<String> cb = new ChoiceBox<>(categories);
		System.out.println(categories);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				categoryAction.getChildren().clear();
				categoryAction.getChildren()
						.add(makeActionTreeItem(categories.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});
		return cb;
	}

	private TreeItem<HBox> makeActionTreeItem(String category) {
		HBox hb = new HBox();
		hb.getChildren().add(makeActionChoiceBox(category));
		return new TreeItem<HBox>(hb);
	}

	private ChoiceBox<String> makeActionChoiceBox(String category) {
		ObservableList<String> actions = FXCollections.observableList(actionFactory.getActions(category));
		ChoiceBox<String> cb = new ChoiceBox<>(actions);
		System.out.println(actions);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				actionAction.getChildren().clear();
				actionAction.getChildren()
						.add(makeActionParameterTreeItem(actions.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});
		return cb;
	}

	private TreeItem<HBox> makeActionParameterTreeItem(String action) {
		HBox hb = new HBox();
		hb.getChildren().add(makeActionParameterChoiceBox(action));
		return new TreeItem<HBox>(hb);
	}

	private ChoiceBox<String> makeActionParameterChoiceBox(String action) {
		ObservableList<String> parameters = FXCollections.observableList(actionFactory.getParameters(action));
		ChoiceBox<String> cb = new ChoiceBox<>(parameters);
		System.out.println(parameters);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				parameterAction.getChildren().clear();
				// parameterAction.getChildren().add(makeOperationTreeItem(parameters.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});

		return cb;
	}

	/**************************** OPERATIONS *****************************/

	private TreeView<HBox> makeOperationTreeView() {
		categoryOperation = makeOperationCategoryTreeItem();
		TreeView<HBox> tv = new TreeView<HBox>(categoryOperation);
		return tv;
	}

	private TreeItem<HBox> makeOperationCategoryTreeItem() {
		HBox hb = new HBox();
		hb.getChildren().add(makeOperationCategoryChoiceBox());
		TreeItem<HBox> ti = new TreeItem<HBox>(hb);
		ti.setExpanded(true);
		return ti;
	}

	private ChoiceBox<String> makeOperationCategoryChoiceBox() {
		// return new ChoiceBox<String>();
		ObservableList<String> parameters = FXCollections.observableList(operationFactory.getOperations("Boolean"));
		ChoiceBox<String> cb = new ChoiceBox<>(parameters);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				actionOperation.getChildren().clear();
				actionOperation.getChildren()
						.add(makeActionOperationTreeItem(parameters.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});

		System.out.println(parameters);
		return cb;
	}
	
	private TreeItem<HBox> makeActionOperationTreeItem(String operation) {
		HBox hb = new HBox();
		hb.getChildren().add(makeOperationCategoryChoiceBox());
		TreeItem<HBox> ti = new TreeItem<HBox>(hb);
		ti.setExpanded(true);
		return ti;
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

	private TreeView<HBox> makeTreeView(ChoiceBox<String> cb) {

		// TreeItem<HBox> title = new TreeItem<HBox>(new HBox());
		// TreeItem<HBox> buttons = new TreeItem<HBox>(createButtonPanel(gameTitle));
		// title.getChildren().add(buttons);
		// title.setExpanded(false);
		//
		// TreeView<HBox> entry = new TreeView<HBox>(title);
		// entry.setOnMouseClicked(e -> resizeTree(title, entry));

		return new TreeView<HBox>(makeTreeItem(cb));
	}

	private TreeItem<HBox> makeTreeItem(ChoiceBox<String> cb) {
		return new TreeItem<HBox>(new HBox(cb));
	}

}
