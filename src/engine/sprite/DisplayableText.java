package engine.sprite;

import gui.player.GameDisplay;

/**
 * 
 * @author Ian Eldridge-Allegra and Nikolas Bramblett
 *
 */
public class DisplayableText implements Displayable{

	public static final DisplayableText DEFAULT = new DisplayableText(Integer.MAX_VALUE, "", "Arial", 12, "#000000");
	private String string;
	private String font;
	private int fontSize;
	private int drawingPriority;
	private double x, y, heading, width, height;
	private String color;
	
	public DisplayableText(int drawingPriority, String string, String font, int fontSize, String webColor) {
		this.string = string;
		this.drawingPriority = drawingPriority;
		this.font = font;
		this.fontSize = fontSize;
		color = webColor;
	}

	@Override
	public void visit(GameDisplay display) {
		display.displayText(this);
	}
	
	public String getText() {
		return string;
	}
	
	public String getFont() {
		return font;
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getHeading() {
		return heading;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}

	@Override
	public int getDrawingPriority() {
		return drawingPriority;
	}

	public String getColor() {
		return color;
	}

	public DisplayableText getWithMessage(String dialogue) {
		return new DisplayableText(drawingPriority, dialogue, font, fontSize, color);
	}
}
