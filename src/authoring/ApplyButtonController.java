package authoring;

import authoring_actionconditions.ActionConditionTab;

public class ApplyButtonController implements ApplyButtonControllerI {
	
	private SpriteObject spriteObject;
	private ActionConditionTab conditionTab;
	private ActionConditionTab actionTab;
	
	public ApplyButtonController(SpriteObject spriteObject,ActionConditionTab conditionTab,ActionConditionTab actionTab) {
		this.spriteObject = spriteObject;
		this.conditionTab = conditionTab;
		this.actionTab = actionTab;
	}

	@Override
	public void updateActionConditionTabs() {
		
	}

	@Override
	public void updateSpriteObject() {
		
	}
	
	

}
