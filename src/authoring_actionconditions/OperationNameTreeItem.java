package authoring_actionconditions;

import engine.operations.OperationFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public class OperationNameTreeItem extends TreeItem<HBox> {

	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";

	private OperationFactory operationFactory = new OperationFactory();
	private ChoiceBox<String> operationCB;
	
	
	public OperationNameTreeItem(String actionParameter) {

		this.makeOperationNameTreeItem(actionParameter);
	}
	
	public String getSelectedOperation() {
		return operationCB.getSelectionModel().getSelectedItem().toString();
	}

	private TreeItem<HBox> makeOperationNameTreeItem(String actionParameter) {
		HBox hb = new HBox();
//		hb.getChildren().addAll(new Label("Choose Operation: "));

		hb.getChildren().add(makeOperationNameChoiceBox(actionParameter, this));
		this.setValue(hb);
		this.setExpanded(true);
		return this;
	}

	private ChoiceBox<String> makeOperationNameChoiceBox(String actionParameter, TreeItem<HBox> operationName) {
		ObservableList<String> operations = FXCollections
				.observableList(operationFactory.getOperations(actionParameter));
		operationCB = new ChoiceBox<>(operations);

		if (actionParameter.equals("Double"))
			operations.add(0, INPUT_A_DOUBLE);
		else if (actionParameter.equals("String"))
			operations.add(0, (INPUT_A_STRING));

		System.out.println("ops: " + operations);

		operationCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				System.out.println("Selected: " + operations.get(operationCB.getSelectionModel().getSelectedIndex()));
				operationName.getChildren().clear();
				operationName.getChildren()
						.add(new OperationParameterTreeItem(operations.get(operationCB.getSelectionModel().getSelectedIndex())));
			}
		});

		return operationCB;
	}

}
