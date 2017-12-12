package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;

import engine.operations.OperationFactory;
import engine.operations.VoogaType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;

public class OperationNameTreeItem extends TreeItem<HBox> {

	private static final String INPUT_A_DOUBLE = "Input a Double";
	private static final String INPUT_A_STRING = "Input a String";
	private static final String INPUT_A_BOOLEAN = "Input a Boolean";

	private OperationFactory operationFactory = new OperationFactory();
	private ChoiceBox<String> operationCB;
	private OperationParameterTreeItem operationParameterTreeItem;
	private List<OperationParameterTreeItem> opParameterList = new ArrayList<>();
	private Runnable changeTreeViewSize;
	private VoogaType voogaType;
	


	private String voogaParameterGetName;

	// public OperationNameTreeItem(String actionParameter) {
	//
	// this.makeOperationNameTreeItem(actionParameter);
	// }

	// public OperationNameTreeItem(VoogaType voogaType, Runnable changeSize) {
	// this(voogaType);
	// this.changeTreeViewSize = changeSize;
	// this.expandedProperty().addListener(e -> changeTreeViewSize.run());
	// }
	//
	// public OperationNameTreeItem(VoogaType voogaType) {
	// this.voogaType = voogaType;
	// this.makeOperationNameTreeItem(voogaType.toString());
	// }

	public OperationNameTreeItem(String voogaParameterGetName, VoogaType voogaType) {
		this.voogaType = voogaType;
		this.voogaParameterGetName = voogaParameterGetName;
		
		this.makeOperationNameTreeItem(voogaType.toString());
	}

	public OperationNameTreeItem(String actionParameterDescription, VoogaType voogaType, Runnable changeSize) {
		this(actionParameterDescription, voogaType);
		this.changeTreeViewSize = changeSize;
		this.expandedProperty().addListener(e -> changeTreeViewSize.run());
		
	}

	public Object makeOperation() {

		if (operationParameterTreeItem.getNumberOfParameters() == 0) {
			return operationParameterTreeItem.getParameter();
		} else {
			System.out.println("there's atleast operation parameter choicebox");
			return operationParameterTreeItem.makeOperation();
		}

	}

	private TreeItem<HBox> makeOperationNameTreeItem(String voogaTypeString) {
		HBox hb = new HBox();
		// hb.getChildren().addAll(new Label("Choose Operation: "));

		hb.getChildren().addAll(new Label(voogaParameterGetName + ": "), makeOperationNameChoiceBox(voogaTypeString, this));
		this.setValue(hb);
		this.setExpanded(true);

		return this;
	}

	private ChoiceBox<String> makeOperationNameChoiceBox(String voogaTypeString, TreeItem<HBox> operationName) {
		ObservableList<String> operations = FXCollections
				.observableList(operationFactory.getOperations(voogaTypeString));

		ObservableList<String> voogaParameters = FXCollections
				.observableList(operationFactory.getOperations(voogaType));
		for (String s : voogaParameters) 
			System.out.println("Vooga Parameters for" + " voogaTypeString: " + s.toString());
		

		operationCB = new ChoiceBox<>(operations);

		if (voogaTypeString.equals("Double"))
			operations.add(0, INPUT_A_DOUBLE);
		else if (voogaTypeString.equals("String"))
			operations.add(0, (INPUT_A_STRING));
		else if (voogaTypeString.equals("Boolean"))
			operations.add(0, (INPUT_A_BOOLEAN));

		System.out.println("ops: " + operations);

		operationCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				System.out.println("Selected Operation: " + operations.get(operationCB.getSelectionModel().getSelectedIndex()));
				operationName.getChildren().clear();
				String selectedOperation = operations.get(operationCB.getSelectionModel().getSelectedIndex());
				
				operationParameterTreeItem = new OperationParameterTreeItem(selectedOperation);
				
				opParameterList.add(operationParameterTreeItem);
				operationName.getChildren().add(operationParameterTreeItem);
			}
		});

		return operationCB;
	}

}
