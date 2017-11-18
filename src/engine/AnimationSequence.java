package engine;

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

	public BoundedImage nextImage() {
		imageIndex = Math.max(0, imageIndex+1);
		return images.get(imageIndex);
	}
}
