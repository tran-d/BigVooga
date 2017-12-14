package authoring_actionconditions;

import java.util.List;

public class ActionVBox<T> extends ActionConditionVBox<T> implements ActionVBoxI<T> {

	public ActionVBox() {
		super();
	}
	
	public ActionVBox(List<T> rows) {
		super(rows);
	}

	@Override
	public void addAction() {
		ActionRow actionRow = new ActionRow(getRows().size() + 1, (ActionVBox<ActionRow>) this);
		addToRows(actionRow);
		new BuildActionView(this, actionRow);
	}
	

}
