package engine.sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * A sequence of BoundedImages that loops.
 * 
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

	/**
	 * Move to the next image
	 */
	public void increment() {
		imageIndex++;
		if(imageIndex >= images.size())
			reset();
	}
	
	/**
	 * Move to the first image
	 */
	public void reset() {
		imageIndex = 0;
	}

	/**
	 * @return The BoundedImage of the current frame
	 */
	public BoundedImage getImage() {
		return images.get(imageIndex);
	}
	
	public AnimationSequence clone()
	{
		List<BoundedImage> clones = new ArrayList<>();
		for(BoundedImage i : images)
		{
			clones.add(i.clone());
		}
		return new AnimationSequence(name, clones);
	}
}
