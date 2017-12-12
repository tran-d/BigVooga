package engine;

import java.util.ArrayList;
import java.util.List;

import engine.Actions.dialog.CloseDialog;
import engine.Actions.dialog.DisplayDialog;
import engine.operations.booleanops.ObjectClicked;
import engine.operations.gameobjectops.Self;
import engine.operations.stringops.SelfString;
import engine.operations.vectorops.LocationOf;
import engine.sprite.AnimationSequence;
import engine.sprite.BoundedImage;
import engine.sprite.DisplayableText;
import engine.sprite.Sprite;

public class DialogSequence extends GameObject {
	public DialogSequence next;
	
	public DialogSequence(String name, String imageForBackground, List<DisplayableText> text) {
		super(name);
		setDialogue(text);
		List<BoundedImage> im = new ArrayList<>();
		im.add(new BoundedImage(imageForBackground));
		AnimationSequence s = new AnimationSequence("Anim", im);
		Sprite sprite = new Sprite();
		sprite.addAnimationSequence(s);
		sprite.setAnimation("Anim");
		setSprite(sprite);
		List<Action> actions = new ArrayList<>();
		actions.add(new CloseDialog(new SelfString(getName())));
		addConditionAction(new Condition(0, new ObjectClicked(new Self())), actions);
	}
	
	public void setNextDialog(DialogSequence next) {
		this.next = next;
		List<Action> actions = new ArrayList<>();
		actions.add(new DisplayDialog(new SelfString(next.getName()), new LocationOf(new Self())));
		addConditionAction(new Condition(0, new ObjectClicked(new Self())), actions);
	}
}
