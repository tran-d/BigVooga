package engine.sprite;

import java.util.List;

import gui.player.GameDisplay;

public class DisplayablePane implements Displayable {

	private DisplayableImage paneImage;
	private List<List<DisplayableImage>> holdableImages;
	private int rowSpan, colSpan;
	
	public DisplayablePane(DisplayableImage paneImage, List<List<DisplayableImage>> holdableImages, int colSpan, int rowSpan) {
		this.paneImage = paneImage;
		this.holdableImages = holdableImages;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
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
		Double cellHeight = inventoryHeight / rowSpan;
		Double cellWidth = inventoryWidth / colSpan;
		for(int r = 0; r < holdableImages.size(); r++) {
			for(int c = 0; c < holdableImages.get(r).size(); c++) {
				DisplayableImage h = holdableImages.get(r).get(c);
				h.setPosition(x0 + (cellWidth * c) + (0.5 * cellWidth), y0 + (cellHeight * r) + (0.5 * cellHeight));
				h.setSize(cellWidth, cellHeight);
				//h.setSize(10, 10);
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
