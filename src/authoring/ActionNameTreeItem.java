package authoring;

import java.util.ArrayList;
import java.util.List;

import authoring_actionconditions.OperationNameTreeItem;
import engine.Actions.ActionFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import tools.DisplayLanguage;

public class ActionNameTreeItem extends TreeItem<HBox> {

	private ActionFactory actionFactory = new ActionFactory();
	private List<String> actionParameterTypes;
	private List<OperationNameTreeItem> opNameTreeItemList;

	// private OperationNameTreeItem operationNameTreeItem1;
	// private OperationNameTreeItem operationNameTreeItem2;

	public ActionNameTreeItem(String actionCategory) {
		this.makeActionTreeItem(actionCategory);
	}

	public void extract() {

		 for (OperationNameTreeItem opItem : opNameTreeItemList) {
		// System.out.println("user inputted op: " + opItem.getSelectedOperation());
		 opItem.makeOperation();
		// System.out.println(opItem.makeOperation());
		
		 }

//		actionFactory.makeAction("Rotate To", new ArrayList<String>() {
//			{
//				add("Self");
//				add("90");
//			}
//		});

		// try {
		// for (String s : actionParameterTypes) {
		// System.out.println(s);
		// }
		//
		// for (OperationNameTreeItem opItem : opItemList)
		// System.out.println("selected op: " + opItem.getSelectedOperation());
		//
		// } catch (Exception e) {
		// showError(e.getMessage(), "blah");
		// }
	}

	private TreeItem<HBox> makeActionTreeItem(String actionCategory) {
		HBox hb = new HBox();
		hb.getChildren().addAll(new Label("Choose Action: "), makeActionNameChoiceBox(actionCategory, this));
		this.setValue(hb);
		this.setExpanded(true);
		return this;
	}

	private ChoiceBox<String> makeActionNameChoiceBox(String actionCategory, TreeItem<HBox> actionTreeItem) {
		ObservableList<String> actions = FXCollections.observableList(actionFactory.getActions(actionCategory));
		ChoiceBox<String> cb = new ChoiceBox<>(actions);
		System.out.println("Acts: " + actions);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				// System.out.println(actions.get(newValue.intValue()));
				// getItems().add(makeParameterChoiceBox(actions.get(newValue.intValue())));
				actionTreeItem.getChildren().clear();
				actionTreeItem.getChildren()
						.add(makeActionParameterTreeItem(actions.get(cb.getSelectionModel().getSelectedIndex())));
			}
		});
		return cb;
	}

	private TreeItem<HBox> makeActionParameterTreeItem(String action) {
		HBox hb = new HBox();
		hb.getChildren().add(new Label("Choose Action Parameter(s)/Operation(s): "));

		TreeItem<HBox> parameterAction = new TreeItem<HBox>(hb);
		makeActionParameterChildren(action, parameterAction, hb);
		parameterAction.setExpanded(true);
		return parameterAction;
	}

	private void makeActionParameterChildren(String action, TreeItem<HBox> parameterAction, HBox hb) {
		ObservableList<String> actionParameterTypes = FXCollections.observableList(actionFactory.getParameters(action));
		this.actionParameterTypes = actionParameterTypes;
		System.out.println("Params: " + actionParameterTypes);
		opNameTreeItemList = new ArrayList<>();

		hb.getChildren().add(new Label("[ "));

		for (String param : actionParameterTypes) {
			hb.getChildren().add(new Label(param + " "));

			OperationNameTreeItem opNameTreeItem = new OperationNameTreeItem(param);
			opNameTreeItemList.add(opNameTreeItem);

			parameterAction.getChildren().add(opNameTreeItem);

		}

		hb.getChildren().add(new Label("]"));
	}

	private void showError(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(header));
		alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(content));
		alert.show();
	}
}
