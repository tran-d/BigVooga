package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import engine.Action;

/**
 * Class representing an action row for sprites.
 * 
 * @author Owen Smith, David Tran
 *
 */
public class ActionRow extends ActionConditionRow {

	private ActionTreeView actionTreeView;
	public static final double ROW_EXPANDED_HEIGHT = ActionConditionRow.EXPANDED_HEIGHT + 50;

	// for creating a new row
	public ActionRow(int ID, ActionVBox<ActionRow> ACVBox,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		setPrefSize(ROW_WIDTH, ROW_EXPANDED_HEIGHT);
		actionTreeView = new ActionTreeView(this,supplier);

		getItems().add(actionTreeView);
	}

	// for loading a selected sprite
	public ActionRow(int ID, ActionVBox<?> ACVBox, ActionTreeView tv,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
		getItems().remove(actionTreeView);
		actionTreeView = tv;
		this.getItems().add(actionTreeView);
	}

	// for loading from xml
	public ActionRow(int ID, ActionVBox<?> ACVBox, List<String> params, Action action,Supplier<List<AbstractSpriteObject>> supplier) {
		super(ID, ACVBox,supplier);
		setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
		actionTreeView = new ActionTreeView(this, params, action,supplier);
		this.getItems().add(actionTreeView);
		this.setPrefSize(ROW_WIDTH, COLLAPSED_HEIGHT);
	}

	/********************** PUBLIC METHODS ***********************/

	public ActionTreeView getTreeView() {
		return actionTreeView;
	}

	public Action getAction() {
		try {
			return actionTreeView.getAction();
		} catch (NullPointerException | NumberFormatException e) {
			throw e;
		}
	}

	public void reduceTreeView() {
		this.getTreeView().getRoot().setExpanded(false);
		this.getTreeView().changeRowTVSize();
	}

	public void expandTreeView() {
		this.getTreeView().getRoot().setExpanded(true);
		this.getTreeView().changeRowTVSize();
	}
}
