package authoring_actionconditions;

import engine.Action;

/**
 * Class representing an action row for sprites.
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ActionRow extends ActionConditionRow {
	
	private ActionTreeView operationTreeView;

	public ActionRow(int ID, ActionVBox<ActionRow> ACVBox) {
		super(ID, ACVBox);
		setPrefSize(ROW_WIDTH, EXPANDED_HEIGHT);
		operationTreeView = new ActionTreeView(this);
		getItems().add(operationTreeView);
	}
	
	public ActionRow(int ID,ActionVBox<?> ACVBox, ActionTreeView tv) {
		super(ID, ACVBox);
		getItems().remove(operationTreeView);
		operationTreeView = tv;
		this.getItems().addAll(operationTreeView);
	}

	/********************** PUBLIC METHODS ***********************/

	public ActionTreeView getTreeView() {
		return operationTreeView;
	}

	public Action getAction() {
		try {
			return operationTreeView.getAction();
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
