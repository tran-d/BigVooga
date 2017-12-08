package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Nikolas Bramblett
 *
 */
public class DisplayableText implements Displayable {

	private String drawing;
	public DisplayableText(String s) {
		// TODO Auto-generated constructor stub
		drawing = s;
	}

	//Text should always be drawn last to avoid overwrites
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void visit(GameDisplay display) {
		// TODO Auto-generated method stub
		
		
	}

}
