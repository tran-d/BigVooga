package authoring_UI;

import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;

public class TopToolBar extends ToolBar implements TopToolBarI {
	
	private ResourceBundle actionTabResources;
	private Button addButton;
	private ChoiceBox<String> options;
	private VBox selectorVBox;
	private Button removeButton;
	private ChoiceBox<Integer> removeRow;
	private VBox removeRowVBox;
	
	public TopToolBar(ResourceBundle resourceBundle,String addButtonTitle,String optionsTitle,String selectorLabel,String remove) {
		super();
		actionTabResources = resourceBundle;
		addButton = new Button(actionTabResources.getString(addButtonTitle));
		options = new ChoiceBox<String>(ActionTabUtil.convertToObservableList(actionTabResources.getString(optionsTitle)));
		selectorVBox = ActionTabUtil.addVBoxwithLabel(actionTabResources.getString(selectorLabel), options);
		Separator separator = ActionTabUtil.makeVerticalSeparator();
		separator.setOrientation(Orientation.VERTICAL);
		removeButton = new Button(actionTabResources.getString(remove));
		removeRow = new ChoiceBox<Integer>();
		removeRowVBox = ActionTabUtil.addVBoxwithLabel(actionTabResources.getString("RemoverLabel"),removeRow);
		getItems().addAll(addButton,options,selectorVBox,separator,removeButton,removeRow,removeRowVBox);
	}

	@Override
	public Integer getRemoveValue() {
		return removeRow.getValue();
	}

	@Override
	public String getOptionsValue() {
		return options.getValue();
	}

	@Override
	public void addButtonListener(EventHandler<ActionEvent> e) {
		addButton.setOnAction(e);
	}

	@Override
	public void addRemoveListener(EventHandler<ActionEvent> e) {
		removeButton.setOnAction(e);
	}

	@Override
	public void addRemoveOption() {
		ObservableList<Integer> currentRows = removeRow.getItems();
		if(currentRows.isEmpty()) {
			currentRows.add(1);
		}
		else {
			int maxrow = 
		}
		removeRow.setItems(currentRows);
	}

	@Override
	public void removeRemoveOption() {
		// TODO Auto-generated method stub
		
	}
	
}
