package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

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
	private OperationParameterTreeItem operationParameterTreeItem;

	private List<OperationParameterTreeItem> opParameterList = new ArrayList<>();
	
	private String selectedOperation;

	public OperationNameTreeItem(String actionParameter) {

		this.makeOperationNameTreeItem(actionParameter);
	}

	public String getSelectedOperation() {
		// return operationCB.getSelectionModel().getSelectedItem().toString();
		

//		if (selectedOperation.equals(INPUT_A_DOUBLE))
//			return operationParameterTreeItem.getParameter();
//		else if (selectedOperation.equals(INPUT_A_STRING))
//			return operationParameterTreeItem.getParameter();
//		else 
//			return "not input a double/string";
		
		return operationParameterTreeItem.getParameter();
		
//		List<String> opParams = new ArrayList<>();
//		// can return null
//		for (OperationParameterTreeItem operationParameterTreeItem : opParameterList) {
//			 opParams.add(operationParameterTreeItem.getParameter());
//			 System.out.println("Operation Name and Parameter: " + selectedOperation + " " + operationParameterTreeItem.getItem());
//		}
//		
//		
//		
//		if (!opParams.isEmpty()) {
//			if (!selectedOperation.equals(INPUT_A_DOUBLE) && !selectedOperation.equals(INPUT_A_STRING)) {
//				return operationFactory.makeOperation(selectedOperation, opParams).toString();
//			}
//			return 
//		}
//		return "";
		
//		return operationParameterTreeItem.getItem();
	}

	private TreeItem<HBox> makeOperationNameTreeItem(String actionParameter) {
		HBox hb = new HBox();
		// hb.getChildren().addAll(new Label("Choose Operation: "));

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

				selectedOperation = operations.get(operationCB.getSelectionModel().getSelectedIndex());
				operationParameterTreeItem = new OperationParameterTreeItem(
						operations.get(operationCB.getSelectionModel().getSelectedIndex()));
				opParameterList.add(operationParameterTreeItem);
				operationName.getChildren().add(operationParameterTreeItem);
			}
		});

		return operationCB;
	}

}
