package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class CompositeImage implements Displayable {

	private DisplayableText message;
	private DisplayableImage sprite;

	private int size;
	private String font;

	public CompositeImage(String font, int size) {
		// TODO Auto-generated constructor stub
		message = null;
		sprite = null;

		this.font = font;
		this.size = size;
	}

	public void setDialogue(String dialogue) {
		message = new DisplayableText(dialogue);
	}

	public void setImage(DisplayableImage i) {
		sprite = i;
	}

	@Override
	public void visit(GameDisplay display) {
		// TODO Auto-generated method stub

		sprite.visit(display);

		//TODO: Make text overlay onto the sprite properly.
		if(message != null)
			message.visit(display);
	}

	//No idea if these should even compare properly
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
