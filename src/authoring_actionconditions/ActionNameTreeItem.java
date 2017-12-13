package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import engine.Action;
import engine.Actions.ActionFactory;
import engine.operations.Operation;
import engine.operations.VoogaParameter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public class ActionNameTreeItem extends TreeItem<HBox> {

	private static String EMPTY_INPUT = "EmptyInput";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String INPUT_A_DOUBLE = "InputInteger";

	private ActionFactory actionFactory = new ActionFactory();
	private List<OperationNameTreeItem> opNameTreeItemList;
	private List<Object> operationList;
	private String selectedAction;
	private Action action;

	// private OperationNameTreeItem operationNameTreeItem1;
	// private OperationNameTreeItem operationNameTreeItem2;

	public ActionNameTreeItem(String actionCategory) {
		this.makeActionTreeItem(actionCategory);
	}

	public Action extract() {
		operationList = new ArrayList<>();
		try {
			for (OperationNameTreeItem opItem : opNameTreeItemList) {

				operationList.add(opItem.makeOperation());
				System.out.println("Operation: " + opItem.makeOperation().toString());

			}
			// System.out.println(operationList);
			System.out.println("Making action for " + selectedAction + "...");
			action = actionFactory.makeAction(selectedAction, operationList.toArray());
			System.out.println(action);
			return action;
		} catch (NullPointerException e) {
			throw e;
			// showError(INVALID_INPUT_MESSAGE, EMPTY_INPUT);
		} catch (NumberFormatException e) {
			throw e;
			// showError(INVALID_INPUT_MESSAGE, INPUT_A_DOUBLE);
		}
		// return null;
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
				selectedAction = actions.get(cb.getSelectionModel().getSelectedIndex());
				actionTreeItem.getChildren().add(makeActionParameterTreeItem(selectedAction));
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

		ObservableList<VoogaParameter> voogaParameters = FXCollections
				.observableList(actionFactory.getParametersWithNames(action));

		System.out.println("Params: " + actionParameterTypes);
		opNameTreeItemList = new ArrayList<>();

		hb.getChildren().add(new Label("[ "));

		for (int i = 0; i < actionParameterTypes.size(); i++) {
			hb.getChildren().add(new Label(actionParameterTypes.get(i) + " "));
			
//			System.out.println("Making Operation for... ActionParameterType: " + actionParameterTypes.get(i)
//					+ " | VoogaParameterName : " + voogaParameters.get(i).getName() + " | VoogaParameterType: "
//					+ voogaParameters.get(i).getType());

			OperationNameTreeItem opNameTreeItem = new OperationNameTreeItem(actionParameterTypes.get(i),
					voogaParameters.get(i).getName(), voogaParameters.get(i).getType());

			opNameTreeItemList.add(opNameTreeItem);
			parameterAction.getChildren().add(opNameTreeItem);
		}
		// for (String param : actionParameterTypes) {
		// hb.getChildren().add(new Label(param + " "));
		//
		// OperationNameTreeItem opNameTreeItem = new OperationNameTreeItem(param);
		// opNameTreeItemList.add(opNameTreeItem);
		// parameterAction.getChildren().add(opNameTreeItem);
		//
		// }

		hb.getChildren().add(new Label("]"));
	}
}
