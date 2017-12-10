package engine.sprite;

import java.util.List;

import gui.player.GameDisplay;

public class DisplayablePane implements Displayable {

	private DisplayableImage paneImage;
	private List<List<DisplayableImage>> holdableImages;
	
	public DisplayablePane(DisplayableImage paneImage, List<List<DisplayableImage>> holdableImages) {
		this.paneImage = paneImage;
		this.holdableImages = holdableImages;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Comparing DisplayablePanes
		return 0;
	}

	@Override
	public void visit(GameDisplay display) {
		display.displayImage(paneImage);
		
		Double inventoryX = paneImage.getX();
		Double inventoryY = paneImage.getY();
		Double inventoryWidth  = paneImage.getWidth();
		Double inventoryHeight = paneImage.getHeight();
		Double x0 = inventoryX - 0.5*inventoryWidth;
		Double y0 = inventoryY - 0.5*inventoryHeight;
		int rowSpan = holdableImages.size();
		Double cellHeight = inventoryHeight / rowSpan;
		for(int r = 0; r < rowSpan; r++) {
			int colSpan = holdableImages.get(r).size();
			Double cellWidth = inventoryWidth / colSpan;
			for(int c = 0; c < colSpan; c++) {
				DisplayableImage h = holdableImages.get(r).get(c);
				h.setPosition(x0 + (cellWidth * c), y0 + (cellHeight * r));
				System.out.println("Holdable Xcor: " + h.getX());
				display.displayImage(h);
			}
		}
		//TODO: display scrollers
	}

	@Override
	public int getDrawingPriority() {
		return Integer.MAX_VALUE;
	}
}
