package authoring;

import authoring_actionconditions.ActionConditionTab;

public class ControllerActionConditionTabsSpriteObject implements ControllerActionConditionTabI{
	
	private SpriteObject spriteObject;
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;

	public ControllerActionConditionTabsSpriteObject(SpriteObject spriteObject,ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.spriteObject = spriteObject;
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
	}

	@Override
	public void loadActionConditions() {
		
	}

	@Override
	public void setActionConditions() {
		
	}
	
}
