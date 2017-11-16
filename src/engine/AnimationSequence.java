package engine;

import java.util.List;

public class AnimationSequence{
	private int imageIndex = 0;
	private List<String> imagePaths;
	private String name;
	
	public AnimationSequence(String name, List<String> imagePaths) {
		this.name = name;
		this.imagePaths = imagePaths;
	}

	public String getName() {
		return name;
	}

	public String nextImage() {
		imageIndex = Math.max(0, imageIndex+1);
		return imagePaths.get(imageIndex);
	}
}
