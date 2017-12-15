package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;

public class ActionVBox<T> extends ActionConditionVBox<T> implements ActionVBoxI<T> {
	
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ActionVBox(Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.supplier = supplier;
	}
	
	public ActionVBox(List<T> rows,Supplier<List<AbstractSpriteObject>> supplier) {
		super(rows);
		this.supplier = supplier;
	}

	@Override
	public void addAction() {
		ActionRow actionRow = new ActionRow(getRows().size() + 1, (ActionVBox<ActionRow>) this,supplier);
		addToRows(actionRow);
		new BuildActionView(this, actionRow);
	}
	

}
