package authoring_UI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class MapLayer extends GridPane {

	private int myLayerNumber;
	private int myRows;
	private int myColumns;
	private SpriteGridHandler mySGH;
	private Color defaultColor;
	private String myName;

	MapLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super();
		defaultColor = c;
		myRows = rows;
		myColumns = columns;
		myLayerNumber = layerNum;
		mySGH = SGH;
		setup();

	}
	
	public String getName(){
		return myName;
	}
	
	protected void setName(String name)
	{
		myName = name;
	}
	public int getLayerNumber() {
		return myLayerNumber;
	}

	protected void setDefaultColor(Color c) {
		defaultColor = c;
	}

	protected Color getDefaultColor() {
		return defaultColor;
	}

	protected void setup() {
		for (int i = 0; i < myColumns; i++) {
			for (int j = 0; j < myRows; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				// "-fx-background-color: rgba(0, 100, 100, 0.5);
				sp.setBackground(
						new Background(new BackgroundFill(getDefaultColor(), CornerRadii.EMPTY, Insets.EMPTY)));
				// sp.setStyle();
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				this.add(sp, i, j);
				mySGH.addDropHandling(sp);
				mySGH.addGridMouseClick(sp);
			}
		}
	}

}
