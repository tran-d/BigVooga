package engine.sprite;

import java.util.List;

import engine.utilities.collisions.RelativeBoundingPolygon;
import gui.player.GameDisplay;

public class BoundedSolidColor extends BoundedImage {

	public BoundedSolidColor(int drawingPriority, String color, List<RelativeBoundingPolygon> bounds) {
		super(drawingPriority, color, bounds);
	}
	
	@Override
	public void visit(GameDisplay disp) {
		disp.drawRectangle(this, getFileName());
	}

}
