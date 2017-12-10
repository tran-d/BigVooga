package engine.sprite;

import java.util.List;
import java.util.Set;

import engine.Element;
import engine.Layer;
import gui.player.GameDisplay;

/**
 * 
 * @author Ian Eldridge-Allegra and Nikolas Bramblett
 *
 */
public class DisplayableText implements Displayable {

	public static final DisplayableText DEFAULT = new DisplayableText(Integer.MAX_VALUE, "", "Arial", 12, "#000000");
	private String string;
	private String font;
	private int fontSize;
	private int drawingPriority;
	private double x, y, heading, width, height;
	private String color;
	
	public DisplayableText(String string, String font, int fontSize, String webColor) {
		this(Integer.MAX_VALUE, string, font, fontSize, webColor);
	}
	
	public DisplayableText(int drawingPriority, String string, String font, int fontSize, String webColor) {
		this.string = string;
		this.drawingPriority = drawingPriority;
		this.font = font;
		this.fontSize = fontSize;
		color = webColor;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return -1;
	}
		
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
	
	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public double getHeading() {
		return heading;
	}
	
	@Override
	public double getWidth() {
		return width;
	}
	
	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public void setHeading(double heading) {
		this.heading = heading;
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

	@Override
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void setSize(double width, double height) {
		this.width = width;
		this.height = height;
	}

}
