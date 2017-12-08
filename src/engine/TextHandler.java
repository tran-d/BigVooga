package engine;

import engine.sprite.BoundedImage;
import engine.sprite.CompositeImage;
import engine.sprite.Displayable;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class TextHandler {

	private String dialogue;

	private String font;
	private int size;

	private final static String DEFAULT_FONT = "Times New Roman";
	private final static int DEFAULT_SIZE = 12;

	public TextHandler() {
		// TODO Auto-generated constructor stub
		this(DEFAULT_FONT, DEFAULT_SIZE);
	}

	public TextHandler(String font, int size) {
		this.font = font;
		this.size = size;
	}

	public void setFont(String s) {
		font = s;
	}

	public void setSize(int i) {
		size = i;
	}

	public void setDialogue(String s) {
		dialogue = s;
	}

	public Displayable makeComposite(BoundedImage sprite) {
		CompositeImage comp = new CompositeImage(font, size);
		comp.setDialogue(dialogue);
		comp.setImage(sprite);
		return comp;
	}

}
