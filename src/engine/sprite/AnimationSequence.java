package engine.sprite;

import java.util.List;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class AnimationSequence{
	private int imageIndex = 0;
	private List<BoundedImage> images;
	private String name;
	
	public AnimationSequence(String name, List<BoundedImage> images) {
		this.name = name;
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void increment() {
		imageIndex++;
		if(imageIndex >= images.size())
			reset();
	}
	
	public void reset() {
		imageIndex = 0;
	}

	public BoundedImage getImage() {
		return images.get(imageIndex);
	}
}
