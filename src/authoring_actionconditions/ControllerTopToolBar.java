package authoring_actionconditions;

public class ControllerTopToolBar {
	
	private TopToolBar topToolBar;
	private ActionConditionVBox actionConditionVBox;
	
	public ControllerTopToolBar(TopToolBar topToolBar,ActionConditionVBox actionConditionVBox) {
		this.topToolBar = topToolBar;
		this.actionConditionVBox = actionConditionVBox;
		addListeners();
	}
	
	private void addListeners() {
		topToolBar.addButtonListener(e -> handleAddingorRemoving(true));
		topToolBar.addRemoveListener(e -> handleAddingorRemoving(false));
	}
	
	private void handleAddingorRemoving(boolean isAdding) {
		System.out.println("I should see this 4 times");
		if(isAdding && !(topToolBar.getOptionsValue() == null)) {
			System.out.println("actions should be adding");
			actionConditionVBox.addActionCondition(topToolBar.getOptionsValue());
			topToolBar.addRemoveOption();
		}
		else if(!isAdding && !(topToolBar.getRemoveValue() == null)) {
			int rowToBeRemoved = topToolBar.getRemoveValue();
			actionConditionVBox.removeActionCondition(rowToBeRemoved - 1);
			topToolBar.removeRemoveOption(rowToBeRemoved - 1);
		}
	}
	
}
