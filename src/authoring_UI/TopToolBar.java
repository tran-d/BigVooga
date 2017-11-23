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
	private RemoveChoiceBox removeRow;
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
		removeRow = new RemoveChoiceBox();
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
		System.out.println("TopToolBar shows it's being added");
		removeRow.addRow();
	}

	@Override
	public void removeRemoveOption(int row) {
		removeRow.removeRow(row - 1);
	}
	
}
