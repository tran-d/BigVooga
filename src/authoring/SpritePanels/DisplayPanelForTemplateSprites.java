package authoring.SpritePanels;

import java.util.function.Consumer;

import authoring.AuthoringEnvironmentManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class DisplayPanelForTemplateSprites extends DisplayPanel{
	
	GameElementSelector elSelector;
	ObjectProperty<Boolean> elementSelectorSpriteActive;
	Consumer<Boolean> OnElementSpriteActiveConsumer;
	

	DisplayPanelForTemplateSprites(AuthoringEnvironmentManager AEM){
		super(AEM);
		elementSelectorSpriteActive = new SimpleObjectProperty<Boolean>();
		elementSelectorSpriteActive.set(false);
		setOnElementSpriteActive(new Consumer<Boolean>(){
			@Override
			public void accept(Boolean newVal) {
				// Nothing by default
					}
			
		});
		elementSelectorSpriteActive.addListener((change, oldVal, newVal)->{
			System.out.println("oldval: "+oldVal);
			System.out.println("newval: "+newVal);
			if (newVal!=oldVal){
			getOnElementSpriteActiveConsumer().accept(newVal);
			}
		});
	}
	
	@Override
	protected void checkMultipleCellsActive() {
		this.setMultipleCellsActive(false);
	}
	
	@Override
	protected void applyToMultipleAtOnce(){
		// Do nothing;
	}
	
	@Override
	protected AbstractSpriteObject getActiveCell() throws Exception {
		return activeSprite;
	}
	
	@Override
	public AbstractSpriteObject setActiveSprite(AbstractSpriteObject ASO) {
		System.out.println("settingActiveSpriteInTemplate");
		AbstractSpriteObject ret = null;
		if (ASO!=null){
			System.out.println("activeSprite: "+activeSprite);
			System.out.println("ASO isnt null");
			if (activeSprite==null || !activeSprite.equals(ASO)) {
				AbstractSpriteObject prevActive = activeSprite;
				activeSprite = ASO;
				
				ret = prevActive;
				System.out.println("prevActive in DPTemplate: "+prevActive);
			}
			elementSelectorSpriteActive.set(true);
		} else {
			elementSelectorSpriteActive.set(false);
		}
		System.out.println("Returning: "+ret);
		return ret;
	}
	
	public void setOnElementSpriteActive(Consumer<Boolean> cons){
		OnElementSpriteActiveConsumer = cons;
	}
	
	public Consumer<Boolean> getOnElementSpriteActiveConsumer(){
		return OnElementSpriteActiveConsumer;
	}
	
	
	@Override
	protected boolean multipleActive(){
		return false;
	}
	
}
