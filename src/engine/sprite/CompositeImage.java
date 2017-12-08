package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class CompositeImage implements Displayable<CompositeImage> {

	private String message;
	private DisplayableImage sprite;

	private int size;
	private String font;

	public CompositeImage(String font, int size) {
		// TODO Auto-generated constructor stub
		message = "";
		sprite = null;

		this.font = font;
		this.size = size;
	}

	public void setDialogue(String dialogue) {
		message = dialogue;
	}

	public void setImage(DisplayableImage i) {
		sprite = i;
	}

	@Override
	public void visit(GameDisplay display) {
		// TODO Auto-generated method stub

		// Draw Image first, then text.
	}

	//Dialogue Boxes should probably always be drawn last.
	@Override
	public int compareTo(CompositeImage o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
