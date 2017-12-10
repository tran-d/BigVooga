package authoring_actionconditions;

import java.util.LinkedList;
import java.util.ResourceBundle;

import ActionConditionClasses.ChoiceBoxVBox;
import ActionConditionClasses.ResourceBundleUtil;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

public class TopToolBar extends ToolBar implements TopToolBarI {

	private ResourceBundle tabResources;
	private Button addButton;
	private ChoiceBoxVBox<String> selectorVBox;
	private Button removeButton;
	private RemoveChoiceBoxVBox removeRowVBox;
//<<<<<<< HEAD:src/authoring_actionconditions/ActionConditionHBox.java
//	private EditChoiceBoxVBox editRowVBox;
//	private Button editButton;
//
//	public ActionConditionHBox(ResourceBundle resourceBundle, String addButtonTitle, String optionsTitle, String selectorLabel,
//			String edit, String remove) {
//		super();
//		
//		this.setAlignment(Pos.BOTTOM_CENTER);
//		this.setPadding(new Insets(5, 0, 5, 5));
//		this.setSpacing(5);
//		this.setPrefWidth(560);
//		tabResources = resourceBundle;
//		addButton = new Button(tabResources.getString(addButtonTitle));
//		ObservableList<String> additionOptions = ActionConditionTabUtil
//				.convertToObservableList(tabResources.getString(optionsTitle));
//		
//		selectorVBox = new ChoiceBoxVBox<String>(tabResources.getString(selectorLabel), additionOptions);
//		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
//		
//		removeButton = new Button(tabResources.getString(remove));
//		removeRowVBox = new RemoveChoiceBoxVBox(tabResources.getString("RemoverLabel"),
//				FXCollections.observableList(new LinkedList<Integer>()));
//		
////		editButton = new Button(tabResources.getString(edit));
////		editRowVBox = new EditChoiceBoxVBox(tabResources.getString("EditLabel"),
////				FXCollections.observableList(new LinkedList<Integer>()));
//		
//		this.getChildren().addAll(addButton, selectorVBox, separator, removeButton, removeRowVBox);
//=======
	
	public TopToolBar(String tabType) {
		super();
		tabResources = ResourceBundleUtil.getResourceBundle(tabType);
		addButton = new Button(tabResources.getString("AddButtonLabel"));
		ObservableList<String> additionOptions = ActionConditionTabUtil.convertToObservableList(tabResources.getString("Options"));
		selectorVBox = new ChoiceBoxVBox<String>(tabResources.getString("SelectorLabel"), additionOptions);
		Separator separator = ActionConditionTabUtil.makeVerticalSeparator();
		removeButton = new Button(tabResources.getString("RemoveButtonLabel"));
		removeRowVBox = new RemoveChoiceBoxVBox(tabResources.getString("RemoverLabel"),FXCollections.observableList(new LinkedList<Integer>()));
		getItems().addAll(addButton,selectorVBox,separator,removeButton,removeRowVBox);
//>>>>>>> 6ea84455ab8d36b09377e73657a9d421ea723e7b:src/authoring_actionconditions/TopToolBar.java
	}
	
	public TopToolBar(String tabType,ObservableList<Integer> actions) {
		this(tabType);
		removeRowVBox.setNewOptions(actions);
	}
	
	@Override
	public ObservableList<Integer> getRemoveRowVBoxOptions() {
		return removeRowVBox.getOptions();
	}

	protected void addRemoveRowVBoxListener(ListChangeListener<Integer> listChangeListener) {
		removeRowVBox.addListChangeListener(listChangeListener);
	}

	@Override
	public Integer getRemoveValue() {
		return (Integer) removeRowVBox.getCurrentValue();
	}

	@Override
	public String getOptionsValue() {
		return (String) selectorVBox.getCurrentValue();
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
		removeRowVBox.addRow();
	}

	@Override
	public void removeRemoveOption(int row) {
		removeRowVBox.removeRow();
	}

}
