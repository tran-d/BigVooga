package engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class Sprite {
	private Map<String, AnimationSequence> animations = new HashMap<String, AnimationSequence>();
	private AnimationSequence currentAnimation;
	
	public void addAnimationSequence(AnimationSequence animation) {
		animations.put(animation.getName(), animation);
	}
	
	public void setAnimation(String name) {
		if(!animations.containsKey(name))
			throw new VoogaException("AnimationNotFound", name);
		currentAnimation = animations.get(name);
		currentAnimation.reset();
	}
	
	public void step() {
		currentAnimation.increment();
	}
	
	public BoundedImage getImage() {
		if(currentAnimation == null)
			throw new VoogaException("UndefinedAnimation");
		return currentAnimation.getImage();
	}
}
