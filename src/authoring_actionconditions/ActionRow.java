package authoring_actionconditions;

import engine.Action;

/**
 * Class representing an action row for sprites.
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ActionRow extends ActionConditionRow {
	
	private ActionTreeView actionTreeView;

	public ActionRow(int ID, ActionVBox<ActionRow> ACVBox) {
		super(ID, ACVBox);
		setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);
		actionTreeView = new ActionTreeView(this);
		getItems().add(actionTreeView);
	}
	
	public ActionRow(int ID,ActionVBox<?> ACVBox, ActionTreeView tv) {
		super(ID, ACVBox);
		getItems().remove(actionTreeView);
		actionTreeView = tv;
		this.getItems().add(actionTreeView);
	}

	/********************** PUBLIC METHODS ***********************/

	public ActionTreeView getTreeView() {
		return actionTreeView;
	}

	public Action getAction() {
		try {
			return actionTreeView.getAction();
		}
		catch(NullPointerException | NumberFormatException e) {
			throw e;
		}
	}
	
	public void reduceTreeView() {
		this.getTreeView().getRoot().setExpanded(false);
		this.getTreeView().changeRowTVSize();
	}
}
