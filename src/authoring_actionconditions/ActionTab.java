package authoring_actionconditions;

import java.util.ResourceBundle;
import ActionConditionClasses.ResourceBundleUtil;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class ActionTab<T> extends Tab implements ActionTabI<T> {
	
	private static final double SPACING = 10;
	
	private ScrollPane actionConditionManager;
	private TopToolBar buttons;
	private ActionConditionVBox<T> actionConditionVBox;
	private ResourceBundle actionTabResources;
	
	public ActionTab(String title) {
		super(title);
		actionTabResources = ResourceBundleUtil.getResourceBundle(title);
		actionConditionManager = new ScrollPane();
		setContent(actionConditionManager);
		setUpActionConditionManager(title);
	}
	
	public ActionTab(String title,ActionConditionVBox<T> actionConditionVBox,TopToolBar topToolBar) {
		this(title);
		this.actionConditionVBox = actionConditionVBox;
		buttons = topToolBar;
	}
                                                                                                                                                                                                                                                                          
	private void setUpActionConditionManager(String title) {
		buttons = new TopToolBar(title);
		actionConditionVBox = setActionConditionVBox();
		VBox mainVBox = new VBox(SPACING);
		mainVBox.getChildren().addAll(buttons,actionConditionVBox);
		actionConditionManager.setContent(mainVBox);
	}
	
	@Override
	public void addTopToolBarListChangeListener(ListChangeListener<Integer> listChangeListener) {
		buttons.addRemoveRowVBoxListener(listChangeListener);
	}
	
	@Override
	public ObservableList<Integer> getCurrentActions() {
		return buttons.getRemoveRowVBoxOptions();
	}
	
	@Override
	public String getActionCondition() {
		return buttons.getOptionsValue();
	}
	
	@Override
	public void addAction(String label) {
		((ActionVBox<T>) actionConditionVBox).addAction(label);
	}
	
	@Override
	public void addRemoveOption() {
		buttons.addRemoveOption();
	}
	
	@Override
	public void removeActionCondtion(Integer row) {
		actionConditionVBox.removeConditionAction(row);
	}
	
	@Override
	public void removeRowOption(Integer row) {
		buttons.removeRemoveOption(row);
	}

	@Override
	public Integer getRemoveValue() {
		return buttons.getRemoveValue();
	}

	@Override
	public void addButtonListener(EventHandler<ActionEvent> e) {
		buttons.addButtonListener(e);
	}

	@Override
	public void addRemoveListener(EventHandler<ActionEvent> e) {
		buttons.addRemoveListener(e);
	}

	@Override
	public ActionConditionVBox<T> getActionConditionVBox() {
		return actionConditionVBox;
	}

	@Override
	public TopToolBar getTopToolBar() {
		return buttons;
	}

	@Override
	public String getSelectorLabel() {
		return actionTabResources.getString("SelectorLabel");
	}
	
	@Override
	public ActionConditionVBox<T> setActionConditionVBox() {
		return new ActionVBox<T>(getSelectorLabel());
	}

	
}
